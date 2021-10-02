package com.example.data.di

import com.example.data.movie.repository.RemoteMovieRepositoryImp
import com.example.data.networking.AuthInterceptor
import com.example.data.networking.RestService
import com.example.domain.movie.repository.MovieRepository
import com.example.domain.movie.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataDIProvider {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        val authInterceptor = OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRestService(retrofit: Retrofit): RestService {
        return retrofit.create(RestService::class.java)
    }

    @Provides
    fun provideRemoteGetMoviesUseCase(restService: RestService): GetMoviesUseCase {
        return GetMoviesUseCase(RemoteMovieRepositoryImp(restService))
    }

}