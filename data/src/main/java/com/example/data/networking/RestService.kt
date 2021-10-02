package com.example.data.networking

import com.example.data.business.movie.dto.GetMoviesResponseDto
import com.example.data.business.show.dto.GetShowsResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestService {

    //Tags endpoints
    @GET("/3/movie/{category}")
    fun getMovies(@Path("category") category: String): Call<GetMoviesResponseDto>

    @GET("/3/tv/{category}")
    fun getTVShows(@Path("category") category: String): Call<GetShowsResponseDto>

}