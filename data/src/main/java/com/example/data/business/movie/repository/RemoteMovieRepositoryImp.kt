package com.example.data.business.movie.repository

import com.example.data.errors.ErrorCodes
import com.example.data.networking.RestService
import com.example.data.persistance.dao.DBMovieDao
import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import timber.log.Timber
import javax.inject.Inject

class RemoteMovieRepositoryImp @Inject constructor(
    private val restService: RestService
) : MovieRepository {

    override fun getMostPopularMovies(page: Int): UseCaseResult {
        try {
            restService.getMostPopularMovies(page).execute().let { response ->
                return if (response.isSuccessful) {
                    UseCaseResult.Success(response.body()?.results)
                } else {
                    UseCaseResult.Error(response.code())
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun getNowPlayingMovies(page: Int): UseCaseResult {
        try {
            restService.getNowPlayingMovies(page).execute().let { response ->
                return if (response.isSuccessful) {
                    UseCaseResult.Success(response.body())
                } else {
                    UseCaseResult.Error(response.code())
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun addMovie(useCaseInput: UseCaseInput): UseCaseResult {
        TODO("Not yet implemented")
    }

}