package com.example.search.source.local.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.search.model.cache.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)


    @Query("SELECT * FROM RemoteKey WHERE id = :id")
    suspend fun findRemoteKey(id: String): RemoteKey?


    @Query("DELETE FROM RemoteKey")
    suspend fun deleteAll()
}
