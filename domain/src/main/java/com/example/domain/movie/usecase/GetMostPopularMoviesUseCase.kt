package com.example.domain.movie.usecase

import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseResult
import javax.inject.Inject

class GetMostPopularMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(page: Int = 1): UseCaseResult {
        return movieRepository.getMostPopularMovies(page)
    }
}