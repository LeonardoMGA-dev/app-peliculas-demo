package com.example.domain.movie.usecase

import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseResult
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(movieId: Int): UseCaseResult {
        return movieRepository.getVideos(movieId)
    }
}