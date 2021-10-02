package com.example.data.movie.repository

import com.example.data.errors.ErrorCodes
import com.example.data.movie.dto.GetMoviesRequestDto
import com.example.data.movie.dto.GetMoviesResponseDto
import com.example.data.networking.RestService
import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import timber.log.Timber
import javax.inject.Inject

class RemoteMovieRepositoryImp @Inject constructor(
    private val restService: RestService
) : MovieRepository {

    override fun getMovies(useCaseInput: UseCaseInput): UseCaseResult {
        val movieCategory = useCaseInput.getData<GetMoviesRequestDto>().category
        try {
            restService.getMovies(movieCategory).execute().let { response ->
                return if (response.isSuccessful) {
                    UseCaseResult.Success(response.body())
                } else {
                    UseCaseResult.Error(response.code())
                }
            }
        } catch (e: Exception) {
            Timber.i(e.message)
            return UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun addMovie(useCaseInput: UseCaseInput): UseCaseResult {
        TODO("Not yet implemented")
    }

}