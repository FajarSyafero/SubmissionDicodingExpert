package com.coco.core.domain.usecase

import com.coco.core.data.source.Resource
import com.coco.core.domain.model.Movie
import com.coco.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val iMovieRepository: IMovieRepository):
    MovieUseCase {
    override fun getMovie(): Flow<Resource<List<Movie>>> =
        iMovieRepository.getMovie()

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        iMovieRepository.searchMovie(query)

    override suspend fun insertFavortieToDb(movie: Movie) =
        iMovieRepository.insertFavoriteToDb(movie)

    override fun getAllFavorite(): Flow<List<Movie>> =
        iMovieRepository.getAllFavorite()

    override suspend fun deleteFavoriteFromDb(movie: Movie) =
        iMovieRepository.deleteFavoriteFromDb(movie)

    override fun isFavorite(id: Int): Flow<Boolean> =
        iMovieRepository.isFavorite(id)
}