package com.example.app_entrevista_grupo_salinas.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding.swipeRefreshLayout.setOnRefreshListener {
            imageGallery.stop()
            homeViewModel.getMoviesFromRemote()
        }
        binding.swipeRefreshLayout.setDistanceToTriggerSync(SWIPE_REFRESH_DISTANCE)
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
        homeViewModel.errorLiveData.observe(
            viewLifecycleOwner,
            errorObserver
        )
    }

    private val contentMediaObserver = Observer<HomeViewModel.MediaContentResult> { result ->
        binding.swipeRefreshLayout.isRefreshing = false
        when (result.mediaCategory) {
            MediaContentCategory.MOST_POPULAR_MOVIES -> {
                val movies = result.movies
                mostPopularMoviesAdapter.setItems(movies)
                imageGallery.setImages(movies.map { "${Constants.BASE_IMAGE_API_URL}${it.backdropPath}" })
                imageGallery.start(lifecycleScope, GALLERY_DELAY_TRANSITION_TIME, GALLERY_ANIMATION_DURATION)
            }
            MediaContentCategory.NOW_PLAYING_MOVIES -> {
                nowPlayingMoviesAdapter.setItems(result.movies)
            }
        }
    }

    private val errorObserver = Observer<Int> { message ->
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val SWIPE_REFRESH_DISTANCE = 400
        private const val GALLERY_DELAY_TRANSITION_TIME = 5000L
        private const val GALLERY_ANIMATION_DURATION = 1000
    }
}