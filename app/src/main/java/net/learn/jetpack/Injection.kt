package net.learn.jetpack

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.repository.MovieRepository
import net.learn.jetpack.service.Api
import net.learn.jetpack.ui.movies.MovieListFactory

object Injection {

    private fun provideMovieRepository(context: Context?): MovieRepository {
        return MovieRepository(Api.create(), AppDatabase.getInstance(context))
    }

    fun provideViewModelFactory(context: Context?): ViewModelProvider.Factory {
        return MovieListFactory(provideMovieRepository(context))
    }
}