package com.coco.submissiondicodingexpert.ui.home

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.coco.core.data.source.Resource
import com.coco.core.domain.model.Movie
import com.coco.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    val getMovie = movieUseCase.getMovie().asLiveData()

    private val _searchResult = MutableLiveData<Resource<List<Movie>>>()
    val searchResult: LiveData<Resource<List<Movie>>> = _searchResult

    fun searchMovie(query: String){
        viewModelScope.launch {
            movieUseCase.searchMovie(query)
                .collect{result ->
                    _searchResult.value = result
                }
        }
    }

}