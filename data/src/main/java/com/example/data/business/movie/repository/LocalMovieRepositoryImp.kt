package com.example.data.business.movie.repository

import com.example.data.business.movie.dto.MovieDto
import com.example.data.business.movie.mapper.toMovieDto
import com.example.data.business.movie.mapper.toRoomEntity
import com.example.data.errors.ErrorCodes
import com.example.data.persistance.AppPreferences
import com.example.data.persistance.dao.DBMovieDao
import com.example.data.utils.Constants
import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import timber.log.Timber
import javax.inject.Inject

class LocalMovieRepositoryImp @Inject constructor(
    private val dbMovieDao: DBMovieDao,
    private val appPreferences: AppPreferences,
) : MovieRepository {

    override fun getMostPopularMovies(page: Int): UseCaseResult {
        return try {
            val offset = (page - 1)  * Constants.QUERY_BATCH_SIZE
            val limit = (page) * Constants.QUERY_BATCH_SIZE
            val data = dbMovieDao.getMostPopularMovies(offset, limit)
            UseCaseResult.Success(data.map { it.toMovieDto() })
        } catch (e: Exception) {
            e.printStackTrace()
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun getNowPlayingMovies(page: Int): UseCaseResult {
        return try {
            val offset = (page - 1) * Constants.QUERY_BATCH_SIZE
            val limit = page * Constants.QUERY_BATCH_SIZE
            Timber.i(appPreferences.minimumRequestDate.toString() + " data")
            Timber.i(appPreferences.maximumRequestDate.toString() + " data")
            dbMovieDao.getNowPlayingMovies(
                offset,
                limit,
                appPreferences.minimumRequestDate,
                appPreferences.maximumRequestDate
            ).let { result ->
                Timber.i(result.size.toString())
                UseCaseResult.Success(result.map { it.toMovieDto() })
            }
        } catch (e: Exception) {
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun addMovie(useCaseInput: UseCaseInput): UseCaseResult {
        return try {
            val movieDto = useCaseInput.getData<MovieDto>()
            dbMovieDao.insertMovie(movieDto.toRoomEntity())
            UseCaseResult.Success()
        } catch (e: Exception) {
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }

    }

    override fun getMovieById(id: Int): UseCaseResult {
        return try {
            val movieEntity = dbMovieDao.getMovieById(id)
            return if(movieEntity != null){
                UseCaseResult.Success(movieEntity.toMovieDto())
            } else {
                UseCaseResult.Error(ErrorCodes.NOT_FOUND)
            }
        } catch (e: Exception) {
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

}