package com.example.domain.show.usecase

import com.example.domain.show.repository.ShowRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(private val showRepository: ShowRepository) {

    operator fun invoke(useCaseInput: UseCaseInput): UseCaseResult {
        return showRepository.getShows(useCaseInput)
    }

}