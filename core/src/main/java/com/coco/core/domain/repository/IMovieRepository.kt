package com.coco.core.domain.repository

import com.coco.core.data.source.Resource
import com.coco.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovie(): Flow<Resource<List<Movie>>>
    fun searchMovie(query:String): Flow<Resource<List<Movie>>>
    suspend fun insertFavoriteToDb(movie: Movie)
    fun getAllFavorite(): Flow<List<Movie>>
    suspend fun deleteFavoriteFromDb(movie: Movie)
    fun isFavorite(id: Int): Flow<Boolean>
}