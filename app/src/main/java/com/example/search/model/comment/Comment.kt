package com.example.search.model.comment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comment")
data class Comment(

        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,


        @ColumnInfo(name = "imageId")
        var imageId: String,


        @ColumnInfo(name = "comment")
        var comment: String,


        @ColumnInfo(name = "commentDate")
        var commentDate: Long)