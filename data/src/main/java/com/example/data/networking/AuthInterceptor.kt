package com.example.data.networking

import com.example.data.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().let { originalRequest ->

            val request = originalRequest.newBuilder()
                .addHeader(Constants.AUTH_HEADER, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmOGUxYjExYzdhODNmZGMwNjJhNDc1NTRiYzZhMTY3YSIsInN1YiI6IjYxNTZkMmE0ZWIxNGZhMDA0M2Q2ZTQ0MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rMtsbwVXF_HTF6x8C7aus9JT8wzOIO5IOETxrsfNjSE")
                .build()

            chain.proceed(request)
        }
    }
}