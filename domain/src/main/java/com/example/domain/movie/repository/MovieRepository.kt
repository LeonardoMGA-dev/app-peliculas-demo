package com.example.domain.movie.repository

import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult

interface MovieRepository {
    fun getMovies(useCaseInput: UseCaseInput): UseCaseResult
    fun addMovie(useCaseInput: UseCaseInput): UseCaseResult
}