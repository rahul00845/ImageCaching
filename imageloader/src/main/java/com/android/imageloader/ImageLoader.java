package com.android.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import com.android.imageloader.caching.ImageCaching;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    private String TAG = "ImageLoader";
    private static ImageLoader instance;
    private static ExecutorService executorService;
    private Context context;
    private ImageLoader() { }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(5);
        }
        return instance;
    }

    public void loadImage(String id, String url, ImageView imageView) {
        ImageCaching.getInstance().init(imageView.getContext());
        this.context = imageView.getContext();
        Bitmap memoryCache = ImageCaching.getInstance().getBitmapFromMemCache(id);
        if (memoryCache != null) {
            imageView.setImageBitmap(memoryCache);
            Log.d(TAG,"Memory");
        } else {
            try {
                Bitmap diskCache = ImageCaching.getInstance().getBitmapFromDiskCache(id);
                if (diskCache != null) {
                    imageView.setImageBitmap(diskCache);
                    Log.d(TAG,"Disk");
                } else {
                    executorService.submit(new Loader(url, imageView, id));
                }
            } catch (IOException e) {
                e.printStackTrace();
                executorService.submit(new Loader(url, imageView, id));
            }
        }
    }

    class Loader implements Runnable {
        String url;
        ImageView imageView;
        String id;
        Loader(String url, ImageView imageView, String id) {
            this.url = url;
            this.imageView = imageView;
            this.id = id;
        }

        @Override
        public void run() {
            Bitmap bitmap = null;
            try {
                bitmap = downloadImageFromUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                ImageCaching.getInstance().addBitmapToCache(id, bitmap);
                if (context instanceof Activity) {
                    final Bitmap finalBitmap = bitmap;
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(finalBitmap);
                        }
                    });
                }
            }
        }
    }

    private Bitmap downloadImageFromUrl (String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) connection.getContent());
        return bitmap;
    }
}
