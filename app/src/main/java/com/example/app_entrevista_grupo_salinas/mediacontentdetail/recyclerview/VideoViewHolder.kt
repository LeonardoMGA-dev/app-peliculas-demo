package com.example.app_entrevista_grupo_salinas.mediacontentdetail.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.app_entrevista_grupo_salinas.databinding.VideoItemBinding
import com.example.app_entrevista_grupo_salinas.utils.Constants
import com.example.data.business.movie.dto.VideoDto

class VideoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = VideoItemBinding.bind(view)

    fun bind(videoDto: VideoDto, onClick: (VideoDto) -> Unit){
        binding.videoItemImageView.setOnClickListener {
            onClick(videoDto)
        }
        Glide.with(view)
            .load("${Constants.BASE_YOUTUBE_THUMBNAIL_URL}${videoDto.key}/hqdefault.jpg")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.videoItemImageView)
    }

}