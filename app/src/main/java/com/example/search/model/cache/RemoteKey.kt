package com.example.search.model.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKey")
data class RemoteKey(

        @PrimaryKey var id: String,

        var nextKey: Int?)
