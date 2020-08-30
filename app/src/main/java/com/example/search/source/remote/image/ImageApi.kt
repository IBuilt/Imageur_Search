package com.example.search.source.remote.image

import com.example.search.model.image.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {

    @GET("gallery/search/{page}")
    suspend fun fetchImages(@Path("page") page: Int, @Query("q") query: String): ImageResponse
}