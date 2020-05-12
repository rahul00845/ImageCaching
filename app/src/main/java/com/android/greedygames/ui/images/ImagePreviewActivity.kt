package com.android.greedygames.ui.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.greedygames.R
import com.android.greedygames.utils.IMAGE_POSITION
import com.android.greedygames.utils.IMAGE_URL
import com.android.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_image_preview.*

class ImagePreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)
        backArrow.setOnClickListener { finish() }
        setData()
    }

    private fun setData() {
        val imageUrl = intent.getStringExtra(IMAGE_URL)
        val imagePosition = intent.getIntExtra(IMAGE_POSITION,0)
        if(imageUrl!=null) {
            ImageLoader.getInstance().loadImage(
                "${image.id}_$imagePosition",
                imageUrl,
                image
            )
        }
    }
}
