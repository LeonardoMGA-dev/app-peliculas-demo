package com.example.data.business.movie.dto

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

    @SerializedName( "backdrop_path")
    val backdropPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val popularity: Double,

    @SerializedName("vote_average")
    val voteAverage: Double,

    val overview: String

)

data class GetMovieVideosResponseDto(
    val id: Int,
    val results: List<VideoDto>
)

data class VideoDto(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
)

