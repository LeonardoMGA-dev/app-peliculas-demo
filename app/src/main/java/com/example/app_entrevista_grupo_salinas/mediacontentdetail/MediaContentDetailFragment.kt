package com.example.app_entrevista_grupo_salinas.mediacontentdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.app_entrevista_grupo_salinas.databinding.FragmentMediaContentDetailBinding
import com.example.app_entrevista_grupo_salinas.mediacontentdetail.viewmodel.MediaContentDetailViewModel
import com.example.data.business.movie.dto.MovieDto
import com.example.data.business.show.dto.ShowDto
import com.example.data.dto.MediaContent
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_MEDIA_CATEGORY = "media_category"
private const val ARG_MEDIA_ID = "media_id"
const val MOVIE_TYPE = "movie"
const val SHOW_TYPE = "show"

@AndroidEntryPoint
class MediaContentDetailFragment : Fragment() {


    private lateinit var binding: FragmentMediaContentDetailBinding
    private val mediaContentDetailViewModel: MediaContentDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediaContentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()

        arguments?.let {
            mediaContentDetailViewModel.getMedia(
                it.getInt(ARG_MEDIA_ID),
                it.getString(ARG_MEDIA_CATEGORY) ?: ""
            )
        }
    }

    private fun setupViewModels() {
        mediaContentDetailViewModel.mediaContentLiveData.observe(
            viewLifecycleOwner,
            mediaContentObserver
        )
    }

    private val mediaContentObserver = Observer<MediaContent> { media ->
        when (media) {
            is MovieDto -> {
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${media.backdrop_path}")
                    .into(binding.mediaContentDetailImageView)
                binding.mediaContentDetailTitle.text = media.title
                binding.mediaContentDetailOverviewText.text = media.overview
            }
        }
    }

    companion object {
        fun newInstance(mediaContent: MediaContent) =
            MediaContentDetailFragment().apply {
                arguments = Bundle().apply {
                    when (mediaContent) {
                        is MovieDto -> {
                            putInt(ARG_MEDIA_ID, mediaContent.id)
                            putString(ARG_MEDIA_CATEGORY, MOVIE_TYPE)
                        }
                        is ShowDto -> {
                            putInt(ARG_MEDIA_ID, mediaContent.id)
                            putString(ARG_MEDIA_CATEGORY, SHOW_TYPE)
                        }
                    }
                }
            }
    }
}