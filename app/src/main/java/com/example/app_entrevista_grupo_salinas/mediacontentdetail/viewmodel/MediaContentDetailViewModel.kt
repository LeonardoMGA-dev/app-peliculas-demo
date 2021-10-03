package com.example.app_entrevista_grupo_salinas.mediacontentdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.business.movie.dto.MovieDto
import com.example.data.business.movie.dto.VideoDto
import com.example.data.di.Local
import com.example.data.di.Remote
import com.example.domain.movie.usecase.GetMovieByIdUseCase
import com.example.domain.movie.usecase.GetMovieVideosUseCase
import com.example.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaContentDetailViewModel @Inject constructor(
    @Local private val getMovieByIdUseCase: GetMovieByIdUseCase,
    @Remote private val getMovieVideosUseCase: GetMovieVideosUseCase
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<MovieDto>()
    val movieLiveData = _movieLiveData as LiveData<MovieDto>

    private val _videosLiveData = MutableLiveData<List<VideoDto>>()
    val videosLiveData = _videosLiveData as LiveData<List<VideoDto>>

    fun getMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieByIdUseCase(id)) {
                is UseCaseResult.Success -> {
                    _movieLiveData.postValue(result.getData<MovieDto>())
                    getVideos(id)
                }
                is UseCaseResult.Error -> {
                }
            }
        }
    }

    private suspend fun getVideos(movieId: Int) = withContext(Dispatchers.IO){
        when (val result = getMovieVideosUseCase(movieId)) {
            is UseCaseResult.Success -> {
                _videosLiveData.postValue(result.getData<List<VideoDto>>())
            }
            is UseCaseResult.Error -> {
                Timber.e(result.errorCode.toString())
            }
        }
    }


}
