package com.example.data.business.movie.dto

import com.example.data.dto.MediaContent
import com.example.data.utils.MediaContentCategory
import com.google.gson.annotations.SerializedName

data class GetMoviesResponseDto(
    val dates: DatesDto? = null,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<MovieDto>
)

data class DatesDto(
    val maximum: String,
    val minimum: String
)

data class MovieDto (
    val id: Int,

    val title: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName( "backdrop_path")
    val backdropPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val popularity: Double,

    @SerializedName("vote_average")
    val voteAverage: Double,

    val overview: String

) : MediaContent

data class GetMoviesRequestDto(
    val category: MediaContentCategory,
    val page: Int
)

