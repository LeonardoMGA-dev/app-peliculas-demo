package com.example.data.networking

import com.example.data.movie.dto.GetMoviesResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface RestService {

    //Tags endpoints
    @GET("/3/movie/{category}")
    fun getMovies(@Path("category") category: String): Call<GetMoviesResponseDto>
}