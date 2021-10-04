package com.example.app_entrevista_grupo_salinas.utils

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.*

class ImageGallery(private val appCompatImageView: AppCompatImageView, private val view: View) {

    private val images = HashSet<String>()
    private var active = false

    fun setImages(images: List<String>) {
        this.images.addAll(images)
    }

    fun start(coroutineScope: CoroutineScope, delayTime: Long, animationDuration: Int) {
        active = true
        coroutineScope.launch(Dispatchers.Main) {
            var iterator = images.iterator()
            while (iterator.hasNext() && active && isActive) {
                Glide.with(view)
                    .load(iterator.next())
                    .transition(DrawableTransitionOptions.withCrossFade(animationDuration))
                    .centerCrop()
                    .into(appCompatImageView)
                delay(delayTime)
                if (!iterator.hasNext()) iterator = images.iterator()
            }
        }
    }

    fun stop() {
        active = false
    }


}