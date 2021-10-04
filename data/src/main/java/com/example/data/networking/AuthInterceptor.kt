package com.example.data.networking

import com.example.data.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().let { originalRequest ->

            val request = originalRequest.newBuilder()
                .addHeader(Constants.AUTH_HEADER, "Bearer ${Constants.API_KEY}")
                .build()

            chain.proceed(request)
        }
    }
}