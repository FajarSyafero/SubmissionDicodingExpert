package com.coco.core.data.source.remote.network


import com.coco.core.data.source.remote.response.GetMovieResponse
import com.coco.core.data.source.remote.response.GetSearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie?api_key=$api_key")
    suspend fun getMovie(): GetMovieResponse

    @GET("search/movie?api_key=$api_key")
    suspend fun getSearchMovie(@Query("query") query: String): GetSearchMovieResponse

    companion object{
        private const val api_key = "56222553e36db739293bd21331563057"
    }
}