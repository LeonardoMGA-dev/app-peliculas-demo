package com.example.app_entrevista_grupo_salinas

import androidx.fragment.app.Fragment
import com.example.data.business.movie.dto.MovieDto
import com.example.data.dto.MediaContent

interface MainNavigation {

    fun launchHomeFragment()
    fun launchMoviesFragment()
    fun launchShowsFragment()
    fun launchMediaContentDetailFragment(movieDto: MovieDto)

}