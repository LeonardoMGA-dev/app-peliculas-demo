package com.example.domain.movie.repository

import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult

interface MovieRepository {
    fun getMostPopularMovies(page: Int = 1): UseCaseResult
    fun getNowPlayingMovies(page: Int = 1): UseCaseResult
    fun addMovie(useCaseInput: UseCaseInput): UseCaseResult
    fun getMovieById(id: Int): UseCaseResult
    fun getVideos(movieId: Int): UseCaseResult
}