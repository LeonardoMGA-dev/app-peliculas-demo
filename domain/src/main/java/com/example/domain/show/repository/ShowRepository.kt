package com.example.domain.show.repository

import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult

interface ShowRepository {
    fun getShows(useCaseInput: UseCaseInput): UseCaseResult
    fun addShow(useCaseInput: UseCaseInput): UseCaseResult
}