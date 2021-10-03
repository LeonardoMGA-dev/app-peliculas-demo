package com.example.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.persistance.dao.DBMovieDao
import com.example.data.persistance.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 4, exportSchema = false )
abstract class LocalDatabase : RoomDatabase() {
    abstract fun movieDao(): DBMovieDao
}