package com.example.search.source.local.image

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.search.model.image.Image

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Images: List<Image>)


    @Query("SELECT * FROM Image Where title LIKE '%' || :queryString || '%'")
    fun imagesByQuery(queryString: String): PagingSource<Int, Image>


    @Query("DELETE FROM Image")
    suspend fun deleteAll()

}