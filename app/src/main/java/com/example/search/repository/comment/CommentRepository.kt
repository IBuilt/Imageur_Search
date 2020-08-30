package com.example.search.repository.comment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.search.constants.Constant.Network.NETWORK_PAGE_SIZE
import com.example.search.source.local.comment.CommentDao
import com.example.search.model.comment.Comment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentRepository @Inject constructor(private val commentDao: CommentDao) {


    fun findComments(imageId: String): Flow<PagingData<Comment>> {

        return Pager(

                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),


                remoteMediator = null,


                pagingSourceFactory = { commentDao.findComments(imageId) }
        ).flow
    }


    suspend fun insert(comment: Comment) {
        commentDao.insert(comment)
    }
}