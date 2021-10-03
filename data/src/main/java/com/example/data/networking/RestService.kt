package com.example.data.networking

import com.example.data.business.movie.dto.GetMovieVideosResponseDto
import com.example.data.business.movie.dto.GetMoviesResponseDto
import com.example.data.business.show.dto.GetShowsResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {


    @GET("/3/movie/popular")
    fun getMostPopularMovies(@Query("page") page: Int = 1): Call<GetMoviesResponseDto>

    @GET("/3/movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int = 1): Call<GetMoviesResponseDto>

    @GET("/3/movie/{id}/videos")
    fun getMovieVideos(@Path("id") id: Int): Call<GetMovieVideosResponseDto>

    @GET("/3/tv/popular")
    fun getMostPopularShows(@Query("page") page: Int = 1): Call<GetShowsResponseDto>

}