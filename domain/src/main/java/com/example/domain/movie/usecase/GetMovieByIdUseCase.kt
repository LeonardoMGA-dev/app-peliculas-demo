package com.example.domain.movie.usecase

import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseResult
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(id: Int): UseCaseResult {
        return movieRepository.getMovieById(id)
    }
}