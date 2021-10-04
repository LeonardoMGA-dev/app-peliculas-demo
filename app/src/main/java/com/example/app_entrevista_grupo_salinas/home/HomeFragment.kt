package com.example.app_entrevista_grupo_salinas.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_entrevista_grupo_salinas.MainNavigation
import com.example.app_entrevista_grupo_salinas.databinding.FragmentHomeBinding
import com.example.app_entrevista_grupo_salinas.home.recyclerview.MediaContentAdapter
import com.example.app_entrevista_grupo_salinas.home.viewmodel.HomeViewModel
import com.example.app_entrevista_grupo_salinas.utils.Constants
import com.example.app_entrevista_grupo_salinas.utils.ImageGallery
import com.example.data.business.movie.dto.MovieDto
import com.example.data.utils.MediaContentCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mostPopularMoviesAdapter: MediaContentAdapter
    private lateinit var nowPlayingMoviesAdapter: MediaContentAdapter
    private lateinit var mainNavigation: MainNavigation
    private lateinit var imageGallery: ImageGallery
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainNavigation = activity as MainNavigation
        imageGallery = ImageGallery(binding.homeGallery, view)
        setupView()
        setupViewModels()
    }

    private fun setupView() {
        mostPopularMoviesAdapter = setupRecyclerview(binding.mostPopularMoviesRecyclerView)
        nowPlayingMoviesAdapter = setupRecyclerview(binding.playingNowMoviesRecyclerView)
        homeViewModel.getMovies()
    }

    private val onClickMediaContent: (MovieDto) -> Unit = { movie ->
        mainNavigation.launchMediaContentDetailFragment(movie)
    }

    private fun setupRecyclerview(recyclerView: RecyclerView): MediaContentAdapter {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return MediaContentAdapter(onClickMediaContent).apply {
            recyclerView.adapter = this
        }
    }

    private fun setupViewModels() {
        homeViewModel.mediaContentLiveData.observe(
            viewLifecycleOwner,
            contentMediaObserver
        )
    }

    private val contentMediaObserver = Observer<HomeViewModel.MediaContentResult> { result ->
        when (result.mediaCategory) {
            MediaContentCategory.MOST_POPULAR_MOVIES -> {
                val movies = result.movies
                mostPopularMoviesAdapter.setItems(movies)
                imageGallery.setImages(movies.map { "${Constants.BASE_IMAGE_API_URL}${it.backdropPath}" })
                imageGallery.start(lifecycleScope, 5000, 1000)
            }
            MediaContentCategory.NOW_PLAYING_MOVIES -> {
                nowPlayingMoviesAdapter.setItems(result.movies)
            }
        }
    }


    companion object {

    }
}