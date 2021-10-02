package com.example.data.business.movie.dto

import com.example.data.dto.MediaContent
import com.google.gson.annotations.SerializedName

data class GetMoviesResponseDto(
    val page: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    val results: List<MovieDto>
)

data class MovieDto (
    val id: Int,

    val title: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String
) : MediaContent

data class GetMoviesRequestDto(
    val category: String
)

