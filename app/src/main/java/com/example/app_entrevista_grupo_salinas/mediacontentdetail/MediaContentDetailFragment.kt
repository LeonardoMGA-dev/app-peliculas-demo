package com.example.app_entrevista_grupo_salinas.mediacontentdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_entrevista_grupo_salinas.databinding.FragmentMediaContentDetailBinding
import com.example.app_entrevista_grupo_salinas.mediacontentdetail.recyclerview.VideoAdapter
import com.example.app_entrevista_grupo_salinas.mediacontentdetail.viewmodel.MediaContentDetailViewModel
import com.example.app_entrevista_grupo_salinas.utils.Constants
import com.example.data.business.movie.dto.MovieDto
import com.example.data.business.movie.dto.VideoDto
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


private const val ARG_MEDIA_ID = "media_id"

@AndroidEntryPoint
class MediaContentDetailFragment : Fragment() {


    private lateinit var binding: FragmentMediaContentDetailBinding
    private val mediaContentDetailViewModel: MediaContentDetailViewModel by viewModels()
    private lateinit var videosAdapter: VideoAdapter

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
            mediaContentDetailViewModel.getMovie(
                it.getInt(ARG_MEDIA_ID),
            )
        }
        setupViews()

    }

    private fun setupViews(){
        videosAdapter =  setupRecyclerview(binding.videosRecyclerViews)
        binding.mediaContentDetailOverviewText.movementMethod = ScrollingMovementMethod()
    }

    private val onClickVideo: (VideoDto) -> Unit = { video ->
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.google.android.youtube")
        intent.data = Uri.parse("${Constants.BASE_YOUTUBE_URL}${video.key}")
        startActivity(intent)
    }


    private fun setupRecyclerview(recyclerView: RecyclerView): VideoAdapter {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return VideoAdapter(onClickVideo).apply {
            recyclerView.adapter = this
        }
    }

    private fun setupViewModels() {
        mediaContentDetailViewModel.movieLiveData.observe(
            viewLifecycleOwner,
            mediaContentObserver
        )
        mediaContentDetailViewModel.videosLiveData.observe(
            viewLifecycleOwner,
            videosObserver
        )

    }

    private val mediaContentObserver = Observer<MovieDto> { movieDto ->
        Glide.with(this)
            .load("${Constants.BASE_IMAGE_API_URL}${movieDto.backdropPath}")
            .into(binding.mediaContentDetailImageView)
        binding.mediaContentDetailTitle.text = movieDto.title
        binding.mediaContentDetailOverviewText.text = movieDto.overview
        binding.releaseDateText.text = movieDto.releaseDate
        binding.voteAverageText.text = movieDto.voteAverage.toString()
    }

    private val videosObserver = Observer<List<VideoDto>> { videos ->
        Timber.i(videos.size.toString())
        videosAdapter.setItems(videos)
    }

    companion object {
        fun newInstance(movieDto: MovieDto) =
            MediaContentDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MEDIA_ID, movieDto.id)
                }
            }
    }
}