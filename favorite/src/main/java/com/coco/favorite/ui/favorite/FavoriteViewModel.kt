package com.coco.favorite.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.coco.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(movieUseCase: MovieUseCase): ViewModel() {
    val getAllFavorite = movieUseCase.getAllFavorite().asLiveData()
}