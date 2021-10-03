package com.example.data.business.movie.mapper

import com.example.data.business.movie.dto.MovieDto
import com.example.data.persistance.entity.MovieEntity
import com.example.data.utils.getMillisFromStringDate

fun MovieDto.toRoomEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        popularity = popularity,
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        releaseDate = releaseDate,
        releaseDateMillis = getMillisFromStringDate(releaseDate, "yyyy-mm-dd")
    )
}


fun MovieEntity.toMovieDto(): MovieDto {
    return MovieDto(
        id = id,
        title = title,
        popularity = popularity,
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        overview = overview
    )
}