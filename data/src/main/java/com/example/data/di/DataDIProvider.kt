package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.business.movie.repository.LocalMovieRepositoryImp
import com.example.data.business.movie.repository.RemoteMovieRepositoryImp
import com.example.data.networking.AuthInterceptor
import com.example.data.networking.RestService
import com.example.data.persistance.AppPreferences
import com.example.data.persistance.LocalDatabase
import com.example.data.persistance.dao.DBMovieDao
import com.example.data.utils.Constants
import com.example.domain.movie.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            .baseUrl(Constants.API_BASE_URL)
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
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return Room.databaseBuilder(appContext, LocalDatabase::class.java, "neflis_db")
            .fallbackToDestructiveMigration()
            .build()
    }


    // movies

    @Provides
    fun provideMovieDao(localDatabase: LocalDatabase): DBMovieDao {
        return localDatabase.movieDao()
    }

    @Provides
    @Remote
    fun providesRemoteGetMostPopularMoviesUseCase(restService: RestService): GetMostPopularMoviesUseCase {
        return GetMostPopularMoviesUseCase(RemoteMovieRepositoryImp(restService))
    }

    @Provides
    @Local
    fun providesLocalGetMostPopularMoviesUseCase(dbMovieDao: DBMovieDao): GetMostPopularMoviesUseCase {
        return GetMostPopularMoviesUseCase(LocalMovieRepositoryImp(dbMovieDao))
    }

    @Provides
    @Remote
    fun providesRemoteGetNowPlayingMoviesUseCase(restService: RestService): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(RemoteMovieRepositoryImp(restService))
    }

    @Provides
    @Local
    fun providesLocalGetNowPlayingMoviesUseCase(dbMovieDao: DBMovieDao): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(LocalMovieRepositoryImp(dbMovieDao))
    }

    @Provides
    @Local
    fun providesLocalAddMoviesUseCase(dbMovieDao: DBMovieDao): AddMovieUseCase {
        return AddMovieUseCase(LocalMovieRepositoryImp(dbMovieDao))
    }

    @Provides
    @Local
    fun providesGetMovieByIdUseCase(dbMovieDao: DBMovieDao): GetMovieByIdUseCase {
        return GetMovieByIdUseCase(LocalMovieRepositoryImp(dbMovieDao))
    }

    @Provides
    @Remote
    fun providesGetMovieVideosUseCase(restService: RestService): GetMovieVideosUseCase {
        return GetMovieVideosUseCase(RemoteMovieRepositoryImp(restService))
    }

}