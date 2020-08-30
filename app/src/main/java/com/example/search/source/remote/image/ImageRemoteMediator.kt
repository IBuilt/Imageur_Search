package com.example.search.source.remote.image

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.search.db.AppDatabase
import com.example.search.source.local.image.ImageDao
import com.example.search.source.local.cache.RemoteKeyDao
import com.example.search.model.image.Image
import com.example.search.model.image.ImageResponse
import com.example.search.model.cache.RemoteKey
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class ImageRemoteMediator(
    private val query: String,
    private val imageApi: ImageApi,
    private val appDatabase: AppDatabase,
    private val remoteKeyDao: RemoteKeyDao,
    private val imageDao: ImageDao
) : RemoteMediator<Int, Image>() {


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Image>): MediatorResult {

        try {


            val loadKey = when (loadType) {

                LoadType.REFRESH -> 0


                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)


                LoadType.APPEND -> {


                    val remoteKey = appDatabase.withTransaction {
                        getRemoteKeyForNextItem(state)
                    }


                    if (remoteKey?.nextKey == null)
                        return MediatorResult.Success(endOfPaginationReached = true)
                    else
                        remoteKey.nextKey!!
                }

            }


            // Fetching data from server
            val imageResponse = imageApi.fetchImages(loadKey, query)


            // Get list of images from gallery response
            val images = getImagesFromSearchResponse(imageResponse)


            val endOfPaginationReached = images.isEmpty()


            appDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {

                    imageDao.deleteAll()

                    remoteKeyDao.deleteAll()
                }


                val nextKey = if (endOfPaginationReached) null else loadKey + 1


                val keys = images.map {
                    RemoteKey(it.id, nextKey)
                }


                remoteKeyDao.insertAll(keys)
                imageDao.insertAll(images)

            }


            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }


    private fun getImagesFromSearchResponse(response: ImageResponse): List<Image> {

        return response.data.map { dataIt ->

            dataIt.images.filter { iFiltered ->

                iFiltered.type.startsWith("Image/", true)

            }.map {

                if (it.title == null) it.title = dataIt.title

                it
            }

        }.flatten()
    }


    private suspend fun getRemoteKeyForNextItem(state: PagingState<Int, Image>): RemoteKey? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { nsImage ->
            remoteKeyDao.findRemoteKey(nsImage.id)
        }
    }
}

