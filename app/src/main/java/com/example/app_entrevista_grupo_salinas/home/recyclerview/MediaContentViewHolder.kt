package com.example.app_entrevista_grupo_salinas.home.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_entrevista_grupo_salinas.databinding.MediaContentItemBinding
import com.example.data.dto.MediaContent
import com.example.data.movie.dto.MovieDto

class MediaContentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = MediaContentItemBinding.bind(view)

    fun bind(mediaContent: MediaContent) {
        when (mediaContent) {
            is MovieDto -> {
                Glide.with(view)
                    .load("https://image.tmdb.org/t/p/w500${mediaContent.posterPath}")
                    .into(binding.mediaContentItemImageView)
            }
        }
    }

}