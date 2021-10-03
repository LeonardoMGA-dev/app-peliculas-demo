package com.example.domain.show.usecase

import com.example.domain.show.repository.ShowRepository
import com.example.domain.util.UseCaseResult
import javax.inject.Inject

class GetMostPopularShowsUseCase @Inject constructor(private val showRepository: ShowRepository) {

    operator fun invoke(page: Int): UseCaseResult {
        return showRepository.getMostPopularShows(page)
    }

}