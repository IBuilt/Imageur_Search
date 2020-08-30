package com.example.search.model.image

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "Image")
data class Image(

        @Json(name = "id")
        @field:Json(name = "id")
        @PrimaryKey
        var id: String = "",

        @Json(name = "title")
        @field:Json(name = "title")
        var title: String?,

        @Json(name = "link")
        @field:Json(name = "link")
        var link: String,

        @Json(name = "type")
        @field:Json(name = "type")
        var type: String,

        var fetchedDate: Long = 0L)