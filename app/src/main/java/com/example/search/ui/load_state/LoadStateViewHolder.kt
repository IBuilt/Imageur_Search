package com.example.search.ui.load_state

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_load_state.view.*

class LoadStateViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.btnRetry.setOnClickListener { retry.invoke() }
    }


    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Error) {
            itemView.tvErrorMsg.text = loadState.error.localizedMessage
        }


        itemView.progressBar.isVisible = loadState is LoadState.Loading


        itemView.btnRetry.isVisible = loadState !is LoadState.Loading


        itemView.tvErrorMsg.isVisible = loadState !is LoadState.Loading
    }
}