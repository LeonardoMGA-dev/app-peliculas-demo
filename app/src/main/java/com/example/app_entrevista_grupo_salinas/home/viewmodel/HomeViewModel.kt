package com.example.app_entrevista_grupo_salinas.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.dto.MediaContent
import com.example.data.movie.dto.GetMoviesRequestDto
import com.example.data.movie.dto.GetMoviesResponseDto
import com.example.data.utils.MediaContentCategory
import com.example.domain.movie.usecase.GetMoviesUseCase
import com.example.domain.util.UseCaseInput
import com.example.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    data class MediaContentResult(
        val mediaContent: List<MediaContent>,
        val mediaCategory: MediaContentCategory
    )

    private val _moviesLiveData = MutableLiveData<MediaContentResult>()
    val moviesLiveData = _moviesLiveData as LiveData<MediaContentResult>

    private fun getMovies(category: MediaContentCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.i(category.name)
            getMoviesUseCase(UseCaseInput(category.categoryName)).let { result ->
                when (result) {
                    is UseCaseResult.Success -> {
                        val data = result.getData<GetMoviesResponseDto>()
                        launch(Dispatchers.Main.immediate) {
                            _moviesLiveData.value = MediaContentResult(data.results, category)
                        }
                    }
                    is UseCaseResult.Error -> {
                        Timber.i(result.errorCode.toString())
                    }
                }
            }
        }
    }

    fun getMediaContent() {
        getMovies(MediaContentCategory.MOST_POPULAR)
        getMovies(MediaContentCategory.NOW_PLAYING)
    }

}