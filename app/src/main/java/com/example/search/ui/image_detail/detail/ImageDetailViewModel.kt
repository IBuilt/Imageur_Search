package com.example.search.ui.image_detail.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lib.utils.DateTimeUtil
import com.example.search.model.comment.Comment
import com.example.search.repository.comment.CommentRepository
import com.example.search.ui.image_detail.comment.CommentAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ImageDetailViewModel @ViewModelInject constructor(private val commentRepo: CommentRepository) :
    ViewModel() {


    lateinit var imageId: String
    lateinit var imageUrl: String
    lateinit var imageTitle: String


    val commentAdapter = CommentAdapter()


    @ExperimentalPagingApi
    fun comments(imageId: String): Flow<PagingData<Comment>> {

        return commentRepo.findComments(imageId)
            .cachedIn(viewModelScope)
    }


    fun insertComment(comment: String) {


        if (comment.isEmpty())
            return


        viewModelScope.launch {

            commentRepo.insert(

                Comment(

                    imageId = imageId,

                    comment = comment,

                    commentDate = DateTimeUtil.nowTimeInMillis()
                )
            )
        }
    }
}