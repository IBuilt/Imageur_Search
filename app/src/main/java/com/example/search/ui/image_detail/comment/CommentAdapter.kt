package com.example.search.ui.image_detail.comment

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lib.utils.DateTimeUtil
import com.example.search.R
import com.example.search.model.comment.Comment
import com.example.lib.utils.inflate
import com.example.lib.utils.whenNotNull
import kotlinx.android.synthetic.main.row_comment.view.*

class CommentAdapter : PagingDataAdapter<Comment, CommentAdapter.ViewHolder>(COMMENT_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.row_comment))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        whenNotNull(getItem(position)) { nsImage ->
            holder.bind(nsImage)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvComment = itemView.tvComment

        private val tvCommentDate = itemView.tvCommentDate


        fun bind(comment: Comment) {

            tvComment.text = comment.comment

            tvCommentDate.text = DateTimeUtil.getRelativeTimeSpanString(comment.commentDate)
        }
    }


    companion object {
        private val COMMENT_COMPARATOR = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem
        }
    }
}