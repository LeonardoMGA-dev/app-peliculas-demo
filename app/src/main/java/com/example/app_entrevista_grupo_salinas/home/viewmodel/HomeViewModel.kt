package com.example.app_entrevista_grupo_salinas.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.di.Local
import com.example.data.di.Remote
import com.example.data.dto.MediaContent
import com.example.data.utils.MediaContentCategory
import com.example.domain.movie.usecase.AddMovieUseCase
import com.example.domain.movie.usecase.GetMostPopularMoviesUseCase
import com.example.domain.movie.usecase.GetNowPlayingMoviesUseCase
import com.example.domain.show.usecase.GetMostPopularShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Remote private val remoteGetMostPopularMoviesUseCase: GetMostPopularMoviesUseCase,
    @Remote private val remoteGetNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    @Remote private val remoteGetMostPopularShowsUseCase: GetMostPopularShowsUseCase,
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

    fun getMovies(category: MediaContentCategory) {

    }

    fun getShows(category: MediaContentCategory) {

    }

}