package com.example.search.di

import android.content.Context
import androidx.room.Room
import com.example.search.db.AppDatabase
import com.example.search.db.AppDatabase.Companion.DB_NAME
import com.example.search.source.remote.image.ImageApi
import com.example.search.source.local.image.ImageDao
import com.example.search.repository.image.ImageRepository
import com.example.search.source.local.cache.RemoteKeyDao
import com.example.search.source.local.comment.CommentDao
import com.example.search.repository.comment.CommentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideImageDao(appDatabase: AppDatabase) =
        appDatabase.imageDao()


    @Provides
    fun provideRemoteKeyDao(appDatabase: AppDatabase) =
        appDatabase.remoteKeyDao()


    @Provides
    fun provideCommentDao(appDatabase: AppDatabase) =
        appDatabase.commentDao()


    @Provides
    fun provideImageRepository(
        imageApi: ImageApi,
        appDatabase: AppDatabase,
        remoteKeyDao: RemoteKeyDao,
        imageDao: ImageDao
    ) =
        ImageRepository(
            imageApi,
            appDatabase,
            remoteKeyDao,
            imageDao
        )


    @Provides
    fun provideCommentRepository(commentDao: CommentDao) =
        CommentRepository(commentDao)
}