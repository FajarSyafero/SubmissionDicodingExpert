package com.coco.core.data.source.repository

import android.util.Log
import com.coco.core.data.source.remote.response.GetMovieResponse
import com.coco.core.data.source.NetworkBoundResource
import com.coco.core.data.source.Resource
import com.coco.core.data.source.local.LocalDataSource
import com.coco.core.data.source.remote.RemoteDataSource
import com.coco.core.data.source.remote.network.ApiResponse
import com.coco.core.domain.model.Movie
import com.coco.core.domain.repository.IMovieRepository
import com.coco.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource):
    IMovieRepository {

    override fun getMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, GetMovieResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<GetMovieResponse>> =
                remoteDataSource.getMovie()

            override fun loadFromNetwork(data: GetMovieResponse): Flow<List<Movie>> =
                DataMapper.mapListResponseToDomain(data.results)
        }.asFlow()

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            try {
                when (val response = remoteDataSource.getSearchMovie(query).first()) {
                    is ApiResponse.Success -> {
                        val movieList = DataMapper.mapSearchResponseToDomain(response.data.results).first()
                        emit(Resource.Success(movieList))
                    }
                    is ApiResponse.Error -> {
                        Log.e("MovieRepository", "searchMovie Error: ${response.errorMessage}")
                        emit(Resource.Error(response.errorMessage))
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieRepository", "searchMovie Exception: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }


    override suspend fun insertFavoriteToDb(movie: Movie) =
        localDataSource.insertFavoriteToDb(DataMapper.mapDomainToEntity(movie))

    override fun getAllFavorite(): Flow<List<Movie>> =
        localDataSource.getAllFavorite().map {
            DataMapper.mapListEntityToDomain(it)
        }

    override suspend fun deleteFavoriteFromDb(movie: Movie) =
        localDataSource.deleteFavoriteFromDb(DataMapper.mapDomainToEntity(movie))

    override fun isFavorite(id: Int): Flow<Boolean> =
        localDataSource.isFavorite(id)
}