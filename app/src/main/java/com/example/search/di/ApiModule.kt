package com.example.search.di

import com.example.search.source.remote.image.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {


    @Provides
    fun provideImageApi(retrofit: Retrofit) =
        retrofit.create(ImageApi::class.java)
}