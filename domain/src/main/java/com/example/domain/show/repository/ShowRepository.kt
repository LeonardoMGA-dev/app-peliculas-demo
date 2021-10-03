package com.example.domain.show.repository

import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult

interface ShowRepository {
    fun getMostPopularShows(page: Int = 1): UseCaseResult
    fun addShow(useCaseInput: UseCaseInput): UseCaseResult
}