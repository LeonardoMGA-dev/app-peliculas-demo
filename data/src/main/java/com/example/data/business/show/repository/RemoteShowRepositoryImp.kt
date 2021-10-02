package com.example.data.business.show.repository

import com.example.data.errors.ErrorCodes
import com.example.data.networking.RestService
import com.example.domain.show.repository.ShowRepository
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult

class RemoteShowRepositoryImp(private val restService: RestService) : ShowRepository {

    override fun getShows(useCaseInput: UseCaseInput): UseCaseResult {
        val category = useCaseInput.getData<String>()
        try {
            restService.getTVShows(category).execute().let { response ->
                return if (response.isSuccessful) {
                    UseCaseResult.Success(response.body())
                } else {
                    UseCaseResult.Error(response.code())
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        }
    }

    override fun addShow(useCaseInput: UseCaseInput): UseCaseResult {
        TODO("Not yet implemented")
    }
}