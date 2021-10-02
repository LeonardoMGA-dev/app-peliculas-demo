package com.example.app_entrevista_grupo_salinas.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_entrevista_grupo_salinas.databinding.FragmentHomeBinding
import com.example.app_entrevista_grupo_salinas.home.recyclerview.MediaContentAdapter
import com.example.app_entrevista_grupo_salinas.home.viewmodel.HomeViewModel
import com.example.data.utils.MediaContentCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mostPopularMoviesAdapter: MediaContentAdapter
    private lateinit var nowPlayingMoviesAdapter: MediaContentAdapter
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
        setupView()
        setupViewModels()
    }

    private fun setupView() {
        homeViewModel.getMediaContent()
        binding.mostPopularMoviesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.mostPopularMoviesRecyclerView.adapter = MediaContentAdapter().apply {
            mostPopularMoviesAdapter = this
        }

        binding.playingNowMoviesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.playingNowMoviesRecyclerView.adapter = MediaContentAdapter().apply {
            nowPlayingMoviesAdapter = this
        }
    }

    private fun setupViewModels() {
        homeViewModel.moviesLiveData.observe(
            viewLifecycleOwner,
            moviesObserver
        )
    }

    private val moviesObserver = Observer<HomeViewModel.MediaContentResult>{ result ->
        when(result.mediaCategory){
            MediaContentCategory.MOST_POPULAR -> {
                mostPopularMoviesAdapter.setItems(result.mediaContent)
            }
            MediaContentCategory.NOW_PLAYING -> {
                nowPlayingMoviesAdapter.setItems(result.mediaContent)
            }
        }
    }

    companion object {

    }
}