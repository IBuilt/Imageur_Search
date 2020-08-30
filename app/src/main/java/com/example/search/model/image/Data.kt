package com.example.search.model.image

import com.squareup.moshi.Json

data class Data(

        @Json(name = "id")
        @field:Json(name = "id")
        val id: String,

        @Json(name = "title")
        @field:Json(name = "title")
        val title: String?,

        @Json(name = "datetime")
        @field:Json(name = "datetime")
        val datetime: Long = 0L,

        @Json(name = "images")
        @field:Json(name = "images")
        val images: List<Image> = emptyList())