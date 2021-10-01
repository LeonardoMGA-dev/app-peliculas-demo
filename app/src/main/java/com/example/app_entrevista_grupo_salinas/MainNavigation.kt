package com.example.app_entrevista_grupo_salinas

interface MainNavigation {

    fun launchHomeFragment()
    fun launchMoviesFragment()
    fun launchShowsFragment()
    fun launchMovieDetailFragment(movieId: Int)
    fun launchShowDetailFragment(showId: Int)

}