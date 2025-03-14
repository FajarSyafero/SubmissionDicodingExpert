package com.coco.core.data.source.remote

import android.util.Log
import com.coco.core.data.source.remote.network.ApiResponse
import com.coco.core.data.source.remote.network.ApiService
import com.coco.core.data.source.remote.response.GetMovieResponse
import com.coco.core.data.source.remote.response.GetSearchMovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovie(): Flow<ApiResponse<GetMovieResponse>>{
        return flow {
            try {
                val response = apiService.getMovie()
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchMovie(query: String): Flow<ApiResponse<GetSearchMovieResponse>> {
        return flow {
            try {
                val response = apiService.getSearchMovie(query)
                emit(ApiResponse.Success(response))
            } catch (e: Exception){
                Log.e("RemoteDataSource", "getSearchMovie Error: ${e.message}", e)
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}