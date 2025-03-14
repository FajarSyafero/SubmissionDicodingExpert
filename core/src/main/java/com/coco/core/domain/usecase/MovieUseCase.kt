package com.coco.core.domain.usecase

import com.coco.core.data.source.Resource
import com.coco.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovie(): Flow<Resource<List<Movie>>>
    fun searchMovie(query: String): Flow<Resource<List<Movie>>>
    suspend fun insertFavortieToDb(movie: Movie)
    fun getAllFavorite(): Flow<List<Movie>>
    suspend fun deleteFavoriteFromDb(movie: Movie)
    fun isFavorite(id:Int): Flow<Boolean>
}