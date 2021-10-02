package com.example.domain.util

class UseCaseInput(private val inputData: Any? = null) {
    fun <R> getData() = inputData as R
}
