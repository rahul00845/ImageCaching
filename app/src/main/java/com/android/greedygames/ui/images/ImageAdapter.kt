package com.android.greedygames.ui.images

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.greedygames.R
import com.android.greedygames.data.models.Children
import com.android.imageloader.ImageLoader
import kotlinx.android.synthetic.main.item_images.view.*

class ImageAdapter(
    private val list: List<Children>,
    private val itemClickListner: ItemClickListner
) : RecyclerView.Adapter<ImageAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.greedyImage

        fun bind(imageData: Children) {
            val thumbnailUrl = imageData.data?.thumbnail ?: ""
            val imageUrl =
                imageData.data?.preview?.images?.get(0)?.source?.imageUrl?.replace("&amp;", "&")
            Log.d("ImageDataUrl", imageUrl)
            Log.d("ImageDataThumb", thumbnailUrl)
            ImageLoader.getInstance().loadImage(
                "${image.id}_$adapterPosition",
                thumbnailUrl,
                image
            )
            itemView.setOnClickListener {
                itemClickListner.onItemClick(imageUrl ?: "", adapterPosition)
            }
        }
    }
}