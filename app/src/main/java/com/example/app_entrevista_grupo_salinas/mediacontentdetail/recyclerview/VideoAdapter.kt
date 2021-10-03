package com.example.app_entrevista_grupo_salinas.mediacontentdetail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_entrevista_grupo_salinas.R
import com.example.app_entrevista_grupo_salinas.home.recyclerview.MediaContentViewHolder
import com.example.data.business.movie.dto.MovieDto
import com.example.data.business.movie.dto.VideoDto

class VideoAdapter(private val onClick: (VideoDto) -> Unit) :
    RecyclerView.Adapter<VideoViewHolder>() {

    private val items = ArrayList<VideoDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
            .let { view ->
                VideoViewHolder(view)
            }
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<VideoDto>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}