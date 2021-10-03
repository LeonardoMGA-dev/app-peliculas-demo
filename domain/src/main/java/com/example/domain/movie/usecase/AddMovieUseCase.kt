package com.example.domain.movie.usecase

import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import javax.inject.Inject

class AddMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(useCaseInput: UseCaseInput): UseCaseResult {
        return movieRepository.addMovie(useCaseInput)
    }
}