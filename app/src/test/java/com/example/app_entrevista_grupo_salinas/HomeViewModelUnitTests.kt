package com.example.app_entrevista_grupo_salinas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.app_entrevista_grupo_salinas.home.viewmodel.HomeViewModel
import com.example.data.business.movie.dto.MovieDto
import com.example.data.errors.ErrorCodes
import com.example.data.utils.MediaContentCategory
import com.example.domain.movie.usecase.AddMovieUseCase
import com.example.domain.movie.usecase.GetMostPopularMoviesUseCase
import com.example.domain.movie.usecase.GetNowPlayingMoviesUseCase
import com.example.domain.util.UseCaseResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals


class HomeViewModelUnitTests {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `now playing movies not found in local`() = runBlocking {
        val mockedRemoteGetMostPopularMoviesUseCase =
            mockk<GetMostPopularMoviesUseCase>(relaxed = true)
        val mockedLocalGetMostPopularMoviesUseCase =
            mockk<GetMostPopularMoviesUseCase>(relaxed = true)
        val mockedRemoteGetNowPlayingMoviesUseCase =
            mockk<GetNowPlayingMoviesUseCase>(relaxed = true)
        val mockedLocalGetNowPlayingMoviesUseCase =
            mockk<GetNowPlayingMoviesUseCase>(relaxed = true)
        val mockedLocalAddMovieUseCase = mockk<AddMovieUseCase>(relaxed = true)

        every { mockedLocalGetNowPlayingMoviesUseCase() } returns UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        every { mockedRemoteGetNowPlayingMoviesUseCase() } returns UseCaseResult.Success(
            ArrayList<MovieDto>()
        )

        val homeViewModel = HomeViewModel(
            mockedRemoteGetMostPopularMoviesUseCase,
            mockedRemoteGetNowPlayingMoviesUseCase,
            mockedLocalGetMostPopularMoviesUseCase,
            mockedLocalGetNowPlayingMoviesUseCase,
            mockedLocalAddMovieUseCase
        )

        homeViewModel.getMovies()

        verifySequence {
            mockedLocalGetNowPlayingMoviesUseCase()
            mockedRemoteGetNowPlayingMoviesUseCase()
        }

    }

    @Test
    fun `most popular movies not found in local`() = runBlocking {
        val mockedRemoteGetMostPopularMoviesUseCase =
            mockk<GetMostPopularMoviesUseCase>(relaxed = true)
        val mockedLocalGetMostPopularMoviesUseCase =
            mockk<GetMostPopularMoviesUseCase>(relaxed = true)
        val mockedRemoteGetNowPlayingMoviesUseCase =
            mockk<GetNowPlayingMoviesUseCase>(relaxed = true)
        val mockedLocalGetNowPlayingMoviesUseCase =
            mockk<GetNowPlayingMoviesUseCase>(relaxed = true)
        val mockedLocalAddMovieUseCase = mockk<AddMovieUseCase>(relaxed = true)

        every { mockedLocalGetMostPopularMoviesUseCase() } returns UseCaseResult.Error(ErrorCodes.EXCEPTION_ON_REQUEST)
        every { mockedRemoteGetMostPopularMoviesUseCase() } returns UseCaseResult.Success(
            ArrayList<MovieDto>()
        )

        val homeViewModel = HomeViewModel(
            mockedRemoteGetMostPopularMoviesUseCase,
            mockedRemoteGetNowPlayingMoviesUseCase,
            mockedLocalGetMostPopularMoviesUseCase,
            mockedLocalGetNowPlayingMoviesUseCase,
            mockedLocalAddMovieUseCase
        )

        homeViewModel.getMovies()

        verifySequence {
            mockedLocalGetMostPopularMoviesUseCase()
            mockedRemoteGetMostPopularMoviesUseCase()
        }

    }


}