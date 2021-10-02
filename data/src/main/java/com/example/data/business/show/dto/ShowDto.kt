package com.example.data.business.show.dto

import com.example.data.dto.MediaContent
import com.google.gson.annotations.SerializedName

data class GetShowsResponseDto(
    val page: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    val results: List<ShowDto>,
)

data class ShowDto(
    val id: Int,

    val name: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val overview: String

) : MediaContent