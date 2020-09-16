package net.learn.jetpack.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.display_fragment.*
import net.learn.jetpack.R
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.favorite.FavoriteRepositoryImpl
import net.learn.jetpack.ui.favorite.viewmodel.FavoriteMovieState
import net.learn.jetpack.ui.favorite.viewmodel.FavoriteMovieViewModelFactory
import net.learn.jetpack.ui.favorite.viewmodel.FavoriteMovieViewModelImpl

class MovieFavoriteFragment : Fragment() {
    private lateinit var vm: FavoriteMovieViewModelImpl
    private lateinit var favoriteAdapter: FavoriteAdapter

    private var favoriteList: ArrayList<Movie>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = FavoriteAdapter()
        rv_movies.apply {
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
        val factory = FavoriteMovieViewModelFactory(FavoriteRepositoryImpl.instance)
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
                is FavoriteMovieState.HideLoading -> toggleLoading(false)
                is FavoriteMovieState.ShowLoading -> toggleLoading(true)
//                is FavoriteMovieState.LoadScreenError ->
            }
        })
    }

    private fun toggleLoading(loading: Boolean) {
        swapRefresh.isRefreshing = loading
    }
}