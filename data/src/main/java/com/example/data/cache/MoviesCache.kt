package com.example.data.cache

import com.example.data.business.movie.dto.MovieDto

class MoviesCache {

    private val data = HashMap<Int, MovieDto>()

    fun getById(id: Int): MovieDto?{
        return data[id]
    }

    fun add(movieDto: MovieDto){
        data[movieDto.id] = movieDto
    }

    fun add(list: List<MovieDto>){
        list.forEach { movie ->
            data[movie.id] = movie
        }
    }

}