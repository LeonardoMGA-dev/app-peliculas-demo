package com.example.data.business.movie.repository

import com.example.data.business.movie.dto.DatesDto
import com.example.data.errors.ErrorCodes
import com.example.data.networking.RestService
import com.example.data.persistance.AppPreferences
import com.example.data.utils.getMillisFromStringDate
import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import javax.inject.Inject

class RemoteMovieRepositoryImp @Inject constructor(
    private val restService: RestService,
    private val appPreferences: AppPreferences
) : MovieRepository {

    private fun setGetNowPlayingMoviesRangeDate(dates: DatesDto){
        appPreferences.minimumRequestDate = getMillisFromStringDate(dates.minimum, "yyyy-MM-dd")
        appPreferences.maximumRequestDate = getMillisFromStringDate(dates.maximum, "yyyy-MM-dd")
    }

    override fun getMostPopularMovies(page: Int): UseCaseResult {
        try {
            restService.getMostPopularMovies(page).execute().let { response ->
                return if (response.isSuccessful) {
                    val result = response.body()?.results
                    UseCaseResult.Success(result)
                } else {
                    UseCaseResult.Error(response.code())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun getNowPlayingMovies(page: Int): UseCaseResult {
        try {
            restService.getNowPlayingMovies(page).execute().let { response ->
                return if (response.isSuccessful) {
                    response.body()?.dates?.let { dates ->
                        setGetNowPlayingMoviesRangeDate(dates)
                    }
                    UseCaseResult.Success(response.body()?.results)
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