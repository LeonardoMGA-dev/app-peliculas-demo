package com.example.app_entrevista_grupo_salinas.mediacontentdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_entrevista_grupo_salinas.mediacontentdetail.MOVIE_TYPE
import com.example.app_entrevista_grupo_salinas.mediacontentdetail.SHOW_TYPE
import com.example.data.cache.MoviesCache
import com.example.data.dto.MediaContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaContentDetailViewModel @Inject constructor(

) : ViewModel() {

    private val _mediaContentLiveData = MutableLiveData<MediaContent>()
    val mediaContentLiveData = _mediaContentLiveData as LiveData<MediaContent>


    fun getMedia(id: Int, mediaType: String){
        viewModelScope.launch(Dispatchers.IO){
            if(mediaType == MOVIE_TYPE) {

            } else {

            }
        }
    }




}