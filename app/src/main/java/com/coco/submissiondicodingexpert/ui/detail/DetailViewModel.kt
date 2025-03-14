package com.coco.submissiondicodingexpert.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.coco.core.domain.model.Movie
import com.coco.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    fun insertFavoriteToDb(movie: Movie) = viewModelScope.launch {
        movieUseCase.insertFavortieToDb(movie)
    }
    fun deleteFavoriteFromDb(movie:Movie) = viewModelScope.launch {
        movieUseCase.deleteFavoriteFromDb(movie)
    }
    fun isFavorite(id:Int) = movieUseCase.isFavorite(id).asLiveData()
}