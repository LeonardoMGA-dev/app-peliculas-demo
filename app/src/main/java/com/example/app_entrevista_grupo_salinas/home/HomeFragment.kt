package com.example.app_entrevista_grupo_salinas.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_entrevista_grupo_salinas.MainNavigation
import com.example.app_entrevista_grupo_salinas.databinding.FragmentHomeBinding
import com.example.app_entrevista_grupo_salinas.home.recyclerview.MediaContentAdapter
import com.example.app_entrevista_grupo_salinas.home.viewmodel.HomeMoviesViewModel
import com.example.data.dto.MediaContent
import com.example.data.utils.MediaContentCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mostPopularMoviesAdapter: MediaContentAdapter
    private lateinit var nowPlayingMoviesAdapter: MediaContentAdapter
    private lateinit var mostPopularShowsAdapter: MediaContentAdapter
    private lateinit var nowPlayingShowsAdapter: MediaContentAdapter
    private lateinit var mainNavigation: MainNavigation
    private val homeMoviesViewModel: HomeMoviesViewModel by viewModels()

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
        setupView()
        setupViewModels()
    }

    private fun setupView() {
        mostPopularMoviesAdapter = setupRecyclerview(binding.mostPopularMoviesRecyclerView)
        nowPlayingMoviesAdapter = setupRecyclerview(binding.playingNowMoviesRecyclerView)
        mostPopularShowsAdapter = setupRecyclerview(binding.mostPopularShowsRecyclerView)
        nowPlayingShowsAdapter = setupRecyclerview(binding.playingNowShowsRecyclerView)
        homeMoviesViewModel.getMovies()
    }

    private val onClickMediaContent: (MediaContent) -> Unit = { mediaContent ->
        mainNavigation.launchMediaContentDetailFragment(mediaContent)
    }

    private fun setupRecyclerview(recyclerView: RecyclerView): MediaContentAdapter {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return MediaContentAdapter(onClickMediaContent).apply {
            recyclerView.adapter = this
        }
    }

    private fun setupViewModels() {
        homeMoviesViewModel.mediaContentLiveData.observe(
            viewLifecycleOwner,
            contentMediaObserver
        )
    }

    private val contentMediaObserver = Observer<HomeMoviesViewModel.MediaContentResult> { result ->
        when (result.mediaCategory) {
            MediaContentCategory.MOST_POPULAR_MOVIES -> {
                mostPopularMoviesAdapter.setItems(result.mediaContent)
            }
            MediaContentCategory.NOW_PLAYING_MOVIES -> {
                nowPlayingMoviesAdapter.setItems(result.mediaContent)
            }
            MediaContentCategory.MOST_POPULAR_SHOWS -> {
                mostPopularShowsAdapter.setItems(result.mediaContent)
            }
            MediaContentCategory.ON_THE_AIR_SHOWS -> {
                nowPlayingShowsAdapter.setItems(result.mediaContent)
            }
        }
    }


    companion object {

    }
}