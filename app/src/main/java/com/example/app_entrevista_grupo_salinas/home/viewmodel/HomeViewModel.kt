package com.example.app_entrevista_grupo_salinas.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.dto.MediaContent
import com.example.data.business.movie.dto.GetMoviesResponseDto
import com.example.data.business.show.dto.GetShowsResponseDto
import com.example.data.cache.MoviesCache
import com.example.data.utils.MediaContentCategory
import com.example.domain.movie.usecase.GetMoviesUseCase
import com.example.domain.show.usecase.GetShowsUseCase
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getShowsUseCase: GetShowsUseCase,
    private val moviesCache: MoviesCache
) : ViewModel() {

    data class MediaContentResult(
        val mediaContent: List<MediaContent>,
        val mediaCategory: MediaContentCategory
    )

    private val _mediaContentLiveData = MutableLiveData<MediaContentResult>()
    val mediaContentLiveData = _mediaContentLiveData as LiveData<MediaContentResult>

    private fun getMovies(category: MediaContentCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.i(category.name)
            getMoviesUseCase(UseCaseInput(category.categoryName)).let { result ->
                when (result) {
                    is UseCaseResult.Success -> {
                        val data = result.getData<GetMoviesResponseDto>()
                        moviesCache.add(data.results)
                        launch(Dispatchers.Main.immediate) {
                            _mediaContentLiveData.value = MediaContentResult(data.results, category)
                        }
                    }
                    is UseCaseResult.Error -> {
                        Timber.i(result.errorCode.toString())
                    }
                }
            }
        }
    }

    private fun getShows(category: MediaContentCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.i(category.name)
            getShowsUseCase(UseCaseInput(category.categoryName)).let { result ->
                when (result) {
                    is UseCaseResult.Success -> {
                        val data = result.getData<GetShowsResponseDto>()
                        launch(Dispatchers.Main.immediate) {
                            _mediaContentLiveData.value = MediaContentResult(data.results, category)
                        }
                    }
                    is UseCaseResult.Error -> {
                        Timber.i(result.errorCode.toString())
                    }
                    else -> {}
                }
            }
        }
    }

    fun getMediaContent() {
        getMovies(MediaContentCategory.MOST_POPULAR_MOVIES)
        getMovies(MediaContentCategory.NOW_PLAYING_MOVIES)
        getShows(MediaContentCategory.MOST_POPULAR_SHOWS)
        getShows(MediaContentCategory.ON_THE_AIR_SHOWS)
    }

}