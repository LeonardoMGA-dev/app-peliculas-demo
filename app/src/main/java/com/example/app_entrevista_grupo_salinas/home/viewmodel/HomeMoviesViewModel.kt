package com.example.app_entrevista_grupo_salinas.home.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.business.movie.dto.MovieDto
import com.example.data.di.Local
import com.example.data.di.Remote
import com.example.data.dto.MediaContent
import com.example.data.utils.Constants
import com.example.data.utils.MediaContentCategory
import com.example.domain.movie.usecase.AddMovieUseCase
import com.example.domain.movie.usecase.GetMostPopularMoviesUseCase
import com.example.domain.movie.usecase.GetNowPlayingMoviesUseCase
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeMoviesViewModel @Inject constructor(
    @Remote private val remoteGetMostPopularMoviesUseCase: GetMostPopularMoviesUseCase,
    @Remote private val remoteGetNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    @Local private val localGetMostPopularMoviesUseCase: GetMostPopularMoviesUseCase,
    @Local private val localGetNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    @Local private val localAddMovieUseCase: AddMovieUseCase,
) : ViewModel() {

    data class MediaContentResult(
        val mediaContent: List<MediaContent>,
        val mediaCategory: MediaContentCategory
    )

    private val _mediaContentLiveData = MutableLiveData<MediaContentResult>()
    val mediaContentLiveData = _mediaContentLiveData as LiveData<MediaContentResult>

    private suspend fun getLocalMostPopularMovies(){
        when(val result = localGetMostPopularMoviesUseCase()) {
            is UseCaseResult.Success -> {
                val movies = result.getData<List<MovieDto>>()
                if(movies.isEmpty()){
                    getRemoteMostPopularMovies()
                }else {
                    withContext(Dispatchers.Main.immediate) {
                        _mediaContentLiveData.value =
                            MediaContentResult(movies, MediaContentCategory.MOST_POPULAR_MOVIES)
                    }
                }
            }
            is UseCaseResult.Error -> {
                getRemoteMostPopularMovies()
            }
        }
    }

    @WorkerThread
    private suspend fun getRemoteMostPopularMovies() {
        remoteGetMostPopularMoviesUseCase().let { useCaseResult ->
            when (useCaseResult) {
                is UseCaseResult.Success -> {
                    val movies = useCaseResult.getData<List<MovieDto>>()
                    withContext(Dispatchers.Main.immediate) {
                        _mediaContentLiveData.value =
                            MediaContentResult(movies, MediaContentCategory.MOST_POPULAR_MOVIES)
                    }
                    movies.forEach { movieDto ->
                        localAddMovieUseCase(UseCaseInput(movieDto))
                    }
                }
                is UseCaseResult.Error -> {
                }
            }
        }
    }

    @WorkerThread
    private suspend fun getRemoteNowPlayingMovies() {
        remoteGetNowPlayingMoviesUseCase().let { useCaseResult ->
            when (useCaseResult) {
                is UseCaseResult.Success -> {
                    val movies = useCaseResult.getData<List<MovieDto>>()
                    withContext(Dispatchers.Main.immediate) {
                        _mediaContentLiveData.value =
                            MediaContentResult(movies, MediaContentCategory.NOW_PLAYING_MOVIES)
                    }
                    movies.forEach { movieDto ->
                        localAddMovieUseCase(UseCaseInput(movieDto))
                    }
                }
                is UseCaseResult.Error -> {
                }
            }
        }
    }

    @WorkerThread
    private suspend fun getLocalNowPlayingMovies() {
        when(val result = localGetNowPlayingMoviesUseCase()) {
            is UseCaseResult.Success -> {
                val movies = result.getData<List<MovieDto>>()
                if(movies.isEmpty()){
                    getRemoteNowPlayingMovies()
                }else {
                    withContext(Dispatchers.Main.immediate) {
                        _mediaContentLiveData.value =
                            MediaContentResult(movies, MediaContentCategory.NOW_PLAYING_MOVIES)
                    }
                }
            }
            is UseCaseResult.Error -> {
                getRemoteNowPlayingMovies()
            }
        }
    }

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getLocalMostPopularMovies()
            getLocalNowPlayingMovies()
        }
    }

}