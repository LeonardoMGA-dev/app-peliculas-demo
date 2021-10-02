package com.example.app_entrevista_grupo_salinas.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_entrevista_grupo_salinas.R
import com.example.data.dto.MediaContent

class MediaContentAdapter : RecyclerView.Adapter<MediaContentViewHolder>() {

    private val items = mutableListOf<MediaContent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaContentViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.media_content_item, parent, false).let { view ->
            MediaContentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MediaContentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<MediaContent>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}