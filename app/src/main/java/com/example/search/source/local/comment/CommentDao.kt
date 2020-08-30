package com.example.search.source.local.comment

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.search.model.comment.Comment

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: Comment)


    @Query("Select * From Comment Where imageId = :imageId Order By commentDate Desc")
    fun findComments(imageId: String): PagingSource<Int, Comment>
}