package com.example.data.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.persistance.entity.MovieEntity

@Dao
interface DBMovieDao {

    @Query("SELECT * FROM movie ORDER BY popularity DESC LIMIT :limit OFFSET :offset")
    fun getMostPopularMovies(offset: Int, limit: Int): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE release_date_millis BETWEEN :minDateMillis AND :maxDateMillis ORDER BY popularity DESC LIMIT :limit OFFSET :offset")
    fun getNowPlayingMovies(
        offset: Int,
        limit: Int,
        minDateMillis: Long,
        maxDateMillis: Long
    ): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE id = :id LIMIT 1")
    fun getMovieById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity)
}