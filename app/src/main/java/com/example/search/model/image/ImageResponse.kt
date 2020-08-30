package com.example.search.model.image

import com.example.search.model.image.Data
import com.squareup.moshi.Json

data class ImageResponse(

    @Json(name = "data")
        @field:Json(name = "data")
        val data: List<Data> = emptyList(),

    @Json(name = "success")
        @field:Json(name = "success")
        val success: Boolean,

    @Json(name = "status")
        @field:Json(name = "status")
        val status: Int)