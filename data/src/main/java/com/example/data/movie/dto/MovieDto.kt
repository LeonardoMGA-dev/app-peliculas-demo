package com.example.data.movie.dto

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class GetMoviesResponseDto(
    val page: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    val results: List<MovieResponseDto>
)

data class MovieResponseDto(
    val id: Int,

    val title: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String
)

data class GetMoviesRequestDto(
    val category: String
)

