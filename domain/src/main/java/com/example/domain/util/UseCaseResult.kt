package com.example.domain.util

sealed class UseCaseResult {
    data class Success(private val inputData: Any? = null) : UseCaseResult() {
        fun <R> getData() = inputData as R
    }
    data class Error(val errorCode: Int, val exception: Throwable? = null): UseCaseResult()
}
