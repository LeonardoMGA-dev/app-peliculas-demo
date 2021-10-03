package com.example.app_entrevista_grupo_salinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.app_entrevista_grupo_salinas.databinding.ActivityMainBinding
import com.example.app_entrevista_grupo_salinas.home.HomeFragment
import com.example.app_entrevista_grupo_salinas.mediacontentdetail.MediaContentDetailFragment
import com.example.data.business.movie.dto.MovieDto
import com.example.data.dto.MediaContent
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

    override fun launchMediaContentDetailFragment(movieDto: MovieDto) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            add(R.id.main_container_view, MediaContentDetailFragment.newInstance(movieDto))
        }
    }


}