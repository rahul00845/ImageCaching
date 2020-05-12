package com.android.imageloader.caching;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;

import com.android.imageloader.caching.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.isExternalStorageRemovable;

public class ImageCaching {
    private LruCache<String, Bitmap> memoryCache;
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;

    private DiskLruCache diskLruCache;
    private final Object diskCacheLock = new Object();
    private boolean diskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 100;
    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    private ImageCaching() { }

    private static ImageCaching instance;

    public static ImageCaching getInstance() {
        if (instance == null) {
            instance = new ImageCaching();
        }
        return instance;
    }

    public void init (Context context) {
        memoryCache = new LruCache<>(cacheSize);
        File cacheDir = getDiskCacheDir(context, DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (diskCacheLock) {
                File cacheDir = params[0];
                try {
                    diskLruCache = DiskLruCache.open(cacheDir, 1,1,DISK_CACHE_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                diskCacheStarting = false; // Finished initialization
                diskCacheLock.notifyAll(); // Wake any waiting threads
            }
            return null;
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }

        synchronized (diskCacheLock) {
            if (diskLruCache != null && diskLruCache.getBitmap(key) == null) {
                diskLruCache.put(key, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromDiskCache(String key) throws IOException {
        synchronized (diskCacheLock) {
            // Wait while disk cache is started from background thread
            while (diskCacheStarting) {
                try {
                    diskCacheLock.wait();
                } catch (InterruptedException e) {}
            }
            if (diskLruCache != null) {
                return diskLruCache.getBitmap(key);
            }
        }
        return null;
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }
}


