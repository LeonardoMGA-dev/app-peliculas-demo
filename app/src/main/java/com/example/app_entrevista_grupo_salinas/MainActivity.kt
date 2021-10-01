package com.example.app_entrevista_grupo_salinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app_entrevista_grupo_salinas.databinding.ActivityMainBinding
import com.example.app_entrevista_grupo_salinas.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainNavigation {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launchHomeFragment()
    }

    override fun launchHomeFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container_view, HomeFragment(), null)
            .commit()
    }

    override fun launchMoviesFragment() {
        TODO("Not yet implemented")
    }

    override fun launchShowsFragment() {
        TODO("Not yet implemented")
    }

    override fun launchMovieDetailFragment(movieId: Int) {
        TODO("Not yet implemented")
    }

    override fun launchShowDetailFragment(showId: Int) {
        TODO("Not yet implemented")
    }

}