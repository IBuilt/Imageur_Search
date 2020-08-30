package com.example.search.repository.image

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.search.constants.Constant.Network.NETWORK_PAGE_SIZE
import com.example.search.db.AppDatabase
import com.example.search.source.remote.image.ImageApi
import com.example.search.source.local.image.ImageDao
import com.example.search.source.remote.image.ImageRemoteMediator
import com.example.search.source.local.cache.RemoteKeyDao
import com.example.search.model.image.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageApi: ImageApi,
    private val appDatabase: AppDatabase,
    private val remoteKeyDao: RemoteKeyDao,
    private val imageDao: ImageDao
) {


    @ExperimentalPagingApi
    fun findSearchImageResult(query: String): Flow<PagingData<Image>> {


        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { imageDao.imagesByQuery(dbQuery) }



        return Pager(

            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),


            remoteMediator = ImageRemoteMediator(

                query,

                imageApi,

                appDatabase,

                remoteKeyDao,

                imageDao
            ),


            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}