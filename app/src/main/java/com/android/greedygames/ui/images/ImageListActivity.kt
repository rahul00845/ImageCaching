package com.android.greedygames.ui.images

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.android.greedygames.R
import com.android.greedygames.data.models.Children
import com.android.greedygames.utils.IMAGE_POSITION
import com.android.greedygames.utils.IMAGE_URL
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ImageListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var imageViewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageViewModel.getNewsList()
        setUpObserver()
    }

    private fun setUpObserver() {
        imageViewModel.getImageListLiveData().observe(this, Observer {
            progressBar.visibility = View.GONE
            it?.let {
                if (it.code == 200) {
                    it.data?.children?.let {images ->
                        if (images.isNotEmpty()) {
                            setAdapter(images)
                        } else {
                            showToast()
                        }
                    } ?: run {
                        showToast()
                    }
                } else {
                    showToast()
                }
            } ?: run {
                showToast()
            }
        })
    }

    private fun setAdapter (list: List<Children>) {
        val adapter = ImageAdapter(list, object: ItemClickListner {
            override fun onItemClick(imageUrl: String, position: Int) {
                val intent = Intent(this@ImageListActivity, ImagePreviewActivity::class.java)
                intent.putExtra(IMAGE_URL, imageUrl)
                intent.putExtra(IMAGE_POSITION, position)
                startActivity(intent)
            }
        })
        imagelist.setItemViewCacheSize(50)
        imagelist.adapter = adapter
    }

    private fun showToast() {
        Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show()
    }


}
