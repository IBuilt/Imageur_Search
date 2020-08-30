package com.example.search.ui.image

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.search.R
import com.example.search.model.image.Image
import com.example.lib.utils.inflate
import com.example.lib.utils.loadUrl
import com.example.lib.utils.whenNotNull
import kotlinx.android.synthetic.main.row_img_ur.view.*

class ImageAdapter : PagingDataAdapter<Image, ImageAdapter.ViewHolder>(IMAGE_COMPARATOR) {


    var onClicked = { _: Image? -> }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.row_img_ur))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        whenNotNull(getItem(position)) { nsImage ->
            holder.bind(nsImage)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onClicked(getItem(absoluteAdapterPosition))
            }
        }

        fun bind(image: Image) {
            itemView.imageView.loadUrl(image.link)
        }
    }


    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem == newItem
        }
    }
}