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
import javax.inject.Inject

class LocalMovieRepositoryImp @Inject constructor(
    private val dbMovieDao: DBMovieDao,
    private val appPreferences: AppPreferences,
) : MovieRepository {

    override fun getMostPopularMovies(page: Int): UseCaseResult {
        return try {
            val offset = page * Constants.QUERY_BATCH_SIZE
            val limit = (page + 1) * Constants.QUERY_BATCH_SIZE
            val data = dbMovieDao.getMostPopularMovies(offset, limit)
            UseCaseResult.Success(data.map { it.toMovieDto() })
        } catch (e: Exception) {
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun getNowPlayingMovies(page: Int): UseCaseResult {
        return try {
            val offset = page * Constants.QUERY_BATCH_SIZE
            val limit = (page + 1) * Constants.QUERY_BATCH_SIZE
            dbMovieDao.getNowPlayingMovies(
                offset,
                limit,
                appPreferences.minimumRequestDate,
                appPreferences.maximumRequestDate
            ).let { result ->
                UseCaseResult.Success(result.map { it.toMovieDto() })
            }
        } catch (e: Exception) {
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun addMovie(useCaseInput: UseCaseInput): UseCaseResult {
        return try {
            val movieDto = useCaseInput.getData<MovieDto>()
            val id = dbMovieDao.insertMovie(movieDto.toRoomEntity())
            UseCaseResult.Success(id)
        } catch (e: Exception) {
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }

    }

}