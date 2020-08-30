package com.example.search.ui.image

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.search.model.image.Image
import com.example.search.repository.image.ImageRepository
import kotlinx.coroutines.flow.Flow

class SearchImageViewModel @ViewModelInject constructor(private val imageRepo: ImageRepository) :
    ViewModel() {


    val imageAdapter = ImageAdapter()


    private var currentQueryValue: String? = null


    private var currentSearchResult: Flow<PagingData<Image>>? = null


    @ExperimentalPagingApi
    fun searchRepo(queryString: String): Flow<PagingData<Image>> {


        val lastResult = currentSearchResult


        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }


        currentQueryValue = queryString


        val newResult = imageRepo.findSearchImageResult(queryString)
            .cachedIn(viewModelScope)


        currentSearchResult = newResult


        return newResult
    }
}