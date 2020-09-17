package net.learn.jetpack.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedList
import kotlinx.android.synthetic.main.favorite_fragment.*
import net.learn.jetpack.R
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.favorite.FavoriteMovieRepositoryImpl
import net.learn.jetpack.ui.favorite.movie.viewmodel.FavoriteMovieState
import net.learn.jetpack.ui.favorite.movie.viewmodel.FavoriteMovieViewModelFactory
import net.learn.jetpack.ui.favorite.movie.viewmodel.FavoriteMovieViewModelImpl
import net.learn.jetpack.utils.makeGone
import net.learn.jetpack.utils.makeVisible

class MovieFavoriteFragment : Fragment() {
    private lateinit var vm: FavoriteMovieViewModelImpl
    private lateinit var favoriteAdapter: FavoriteMovieAdapter

    private var favoriteList: ArrayList<Movie>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = FavoriteMovieAdapter()
        rv_movies.apply {
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
        val factory = FavoriteMovieViewModelFactory(FavoriteMovieRepositoryImpl.instance)
        vm = ViewModelProvider(this, factory).get(FavoriteMovieViewModelImpl::class.java)

        initData()
        initObserver()
    }

    private fun initData() {
        lifecycleScope.launchWhenCreated {
            vm.getFavoriteMovie()
        }
    }

    private fun initObserver() {
        vm.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is FavoriteMovieState.HideLoading -> {
                    pbFavorite.makeGone()
                }
                is FavoriteMovieState.ShowLoading -> {
                    pbFavorite.makeVisible()
                }
                is FavoriteMovieState.LoadScreenError -> {
                    loadErrorScreen()
                }
                is FavoriteMovieState.LoadEmptyScreen -> {
                    loadEmptyScreen()
                }
                is FavoriteMovieState.LoadFavoriteMovie -> {
                    pbFavorite.makeGone()
                    showData()
                }
            }
        })
    }

    private fun showData() {
        lifecycleScope.launchWhenCreated {
            vm.movie()?.observe(viewLifecycleOwner, observer)
        }
    }

    private val observer = Observer<PagedList<Movie>> {
        favoriteAdapter.submitList(it)
    }

    private fun loadEmptyScreen() {
        rv_movies.makeGone()
        pbFavorite.makeGone()
        layoutEmpty.makeVisible()
    }

    private fun loadErrorScreen() {
        rv_movies.makeGone()
        pbFavorite.makeGone()
        layoutError.makeVisible()
    }
}