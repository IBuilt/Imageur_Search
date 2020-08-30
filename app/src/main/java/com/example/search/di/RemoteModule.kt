package com.example.search.di

import com.example.search.constants.Constant
import com.example.lib.remote.interceptor.DefaultInterceptor
import com.example.search.source.remote.Endpoint
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoint.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(DefaultInterceptor())
            .connectTimeout(Constant.Network.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.Network.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.Network.TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}