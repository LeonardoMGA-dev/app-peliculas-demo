package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.data.business.movie.repository.LocalMovieRepositoryImp
import com.example.data.business.movie.repository.RemoteMovieRepositoryImp
import com.example.data.networking.AuthInterceptor
import com.example.data.networking.RestService
import com.example.data.persistance.AppPreferences
import com.example.data.persistance.LocalDatabase
import com.example.data.persistance.dao.DBMovieDao
import com.example.data.utils.Constants.APP_SHARED_PREFERENCE_NAME
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
    @Singleton
    fun provideAppSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAppPreferences(sharedPreferences: SharedPreferences): AppPreferences {
        return AppPreferences(sharedPreferences)
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
    fun providesRemoteGetMostPopularMoviesUseCase(
        restService: RestService,
        appPreferences: AppPreferences
    ): GetMostPopularMoviesUseCase {
        return GetMostPopularMoviesUseCase(RemoteMovieRepositoryImp(restService, appPreferences))
    }

    @Provides
    @Local
    fun providesLocalGetMostPopularMoviesUseCase(
        dbMovieDao: DBMovieDao,
        appPreferences: AppPreferences
    ): GetMostPopularMoviesUseCase {
        return GetMostPopularMoviesUseCase(LocalMovieRepositoryImp(dbMovieDao, appPreferences))
    }

    @Provides
    @Remote
    fun providesRemoteGetNowPlayingMoviesUseCase(
        restService: RestService,
        appPreferences: AppPreferences
    ): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(RemoteMovieRepositoryImp(restService, appPreferences))
    }

    @Provides
    @Local
    fun providesLocalGetNowPlayingMoviesUseCase(
        dbMovieDao: DBMovieDao,
        appPreferences: AppPreferences
    ): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(LocalMovieRepositoryImp(dbMovieDao, appPreferences))
    }

    @Provides
    @Local
    fun providesLocalAddMoviesUseCase(
        dbMovieDao: DBMovieDao,
        appPreferences: AppPreferences
    ): AddMovieUseCase {
        return AddMovieUseCase(LocalMovieRepositoryImp(dbMovieDao, appPreferences))
    }

    @Provides
    @Local
    fun providesGetMovieByIdUseCase(
        dbMovieDao: DBMovieDao,
        appPreferences: AppPreferences
    ): GetMovieByIdUseCase {
        return GetMovieByIdUseCase(LocalMovieRepositoryImp(dbMovieDao, appPreferences))
    }


    @Provides
    @Remote
    fun providesGetMovieVideosUseCase(
        restService: RestService,
        appPreferences: AppPreferences
    ): GetMovieVideosUseCase {
        return GetMovieVideosUseCase(RemoteMovieRepositoryImp(restService, appPreferences))
    }

}