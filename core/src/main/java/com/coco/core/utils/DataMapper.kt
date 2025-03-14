package com.coco.core.utils

import com.coco.core.data.source.local.entity.FavoriteEntity
import com.coco.core.data.source.remote.response.Result
import com.coco.core.data.source.remote.response.ResultsItem
import com.coco.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    private const val IMG_PATH = "https://image.tmdb.org/t/p/w500"

    fun mapListResponseToDomain(movieResponse:List<Result>): Flow<List<Movie>> {
        val movieList = ArrayList<Movie>()
        movieResponse.map {
            val movie = Movie(
                IMG_PATH +it.posterPath,
                it.title,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount.toDouble(),
                it.runtime,
                it.releaseDate,
                false
            )
            movieList.add(movie)
        }
        return flowOf(movieList)
    }

    fun mapSearchResponseToDomain(movieResponse: List<ResultsItem>): Flow<List<Movie>> {
        val movieList = ArrayList<Movie>()
        movieResponse.map {
            val movie = Movie(
                if (it.posterPath != null) IMG_PATH + it.posterPath else null,
                it.title,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount.toDouble(),
                null,
                it.releaseDate,
                false
            )
            movieList.add(movie)
        }
        return flowOf(movieList)
    }
    fun mapDomainToEntity(movie:Movie) =
        FavoriteEntity(
            movie.id,
            movie.img,
            movie.name,
            movie.overview,
            movie.voteAverage,
            movie.voteCount,
            movie.runtime,
            movie.releaseDate,
            movie.isFavorite
        )
    fun mapListEntityToDomain(listMovieDiscoverEntity: List<FavoriteEntity>): List<Movie> =
        listMovieDiscoverEntity.map {
            Movie(
                it.img,
                it.name,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount,
                it.runtime,
                it.releaseDate,
                it.isFavorite
            )
        }

}