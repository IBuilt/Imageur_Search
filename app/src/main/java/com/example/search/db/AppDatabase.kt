package com.example.search.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.search.converters.room.DateConverter
import com.example.search.source.local.image.ImageDao
import com.example.search.source.local.cache.RemoteKeyDao
import com.example.search.source.local.comment.CommentDao
import com.example.search.model.image.Image
import com.example.search.model.cache.RemoteKey
import com.example.search.model.comment.Comment


@Database(
    entities = [Image::class, RemoteKey::class, Comment::class],
    version = 1, exportSchema = false
)

@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun commentDao(): CommentDao


    companion object {
        const val DB_NAME = "Database"
    }
}