package com.example.app_entrevista_grupo_salinas.home.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.app_entrevista_grupo_salinas.databinding.MediaContentItemBinding
import com.example.app_entrevista_grupo_salinas.utils.Constants
import com.example.data.business.movie.dto.MovieDto

class MediaContentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = MediaContentItemBinding.bind(view)

    fun bind(mediaContent: MovieDto, onClick: (MovieDto) -> Unit) {
        binding.mediaContentItemImageView.setOnClickListener {
            onClick(mediaContent)
        }
        Glide.with(view)
            .load("${Constants.BASE_IMAGE_API_URL}${mediaContent.posterPath}")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.mediaContentItemImageView)
    }

}