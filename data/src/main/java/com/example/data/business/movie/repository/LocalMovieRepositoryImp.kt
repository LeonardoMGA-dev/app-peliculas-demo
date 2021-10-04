package com.example.data.business.movie.repository

import com.example.data.business.movie.mapper.toMovieDto
import com.example.data.errors.ErrorCodes
import com.example.data.persistance.dao.DBMovieDao
import com.example.data.persistance.entity.MovieEntity
import com.example.data.utils.Constants
import com.example.domain.movie.repository.MovieRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import timber.log.Timber
import javax.inject.Inject

class LocalMovieRepositoryImp @Inject constructor(
    private val dbMovieDao: DBMovieDao
) : MovieRepository {

    override fun getMostPopularMovies(page: Int): UseCaseResult {
        return try {
            val offset = (page - 1) * Constants.QUERY_BATCH_SIZE
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
            dbMovieDao.getNowPlayingMovies(
                offset,
                limit
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
            val movieEntity = useCaseInput.getData<MovieEntity>()
            dbMovieDao.insertMovie(movieEntity)
            UseCaseResult.Success()
        } catch (e: Exception) {
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }

    }

    override fun getMovieById(id: Int): UseCaseResult {
        return try {
            val movieEntity = dbMovieDao.getMovieById(id)
            return if (movieEntity != null) {
                UseCaseResult.Success(movieEntity.toMovieDto())
            } else {
                UseCaseResult.Error(ErrorCodes.NOT_FOUND)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun getVideos(movieId: Int): UseCaseResult {
        TODO("Not yet implemented")
    }

}