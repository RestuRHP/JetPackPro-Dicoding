package net.learn.jetpack.ui.favorite.tv

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
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.favorite.FavoriteTvRepositoryImpl
import net.learn.jetpack.ui.favorite.tv.viewmodel.FavoriteTvState
import net.learn.jetpack.ui.favorite.tv.viewmodel.FavoriteTvViewModelFactory
import net.learn.jetpack.ui.favorite.tv.viewmodel.FavoriteTvViewModelImpl
import net.learn.jetpack.utils.makeGone
import net.learn.jetpack.utils.makeVisible

class TvFavoriteFragment : Fragment() {
    private lateinit var vm: FavoriteTvViewModelImpl
    private lateinit var favoriteAdapter: FavoriteTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = FavoriteTvAdapter()
        rv_movies_favorite.apply {
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
        val factory = FavoriteTvViewModelFactory(FavoriteTvRepositoryImpl.instance)
        vm = ViewModelProvider(this, factory).get(FavoriteTvViewModelImpl::class.java)

        initData()
        initObserver()
    }

    private fun initData() {
        lifecycleScope.launchWhenCreated {
            vm.getFavoriteTv()
        }
    }

    private fun initObserver() {
        vm.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is FavoriteTvState.HideLoading -> {
                    pbFavorite.makeGone()
                }
                is FavoriteTvState.ShowLoading -> {
                    pbFavorite.makeVisible()
                }
                is FavoriteTvState.LoadScreenError -> {
                    loadErrorScreen()
                }
                is FavoriteTvState.LoadEmptyScreen -> {
                    loadEmptyScreen()
                }
                is FavoriteTvState.LoadFavoriteMovie -> {
                    pbFavorite.makeGone()
                    showData()
                }
            }
        })
    }

    private fun showData() {
        lifecycleScope.launchWhenCreated {
            vm.tvShow()?.observe(viewLifecycleOwner, observer)
        }
    }

    private val observer = Observer<PagedList<TvShow>> {
        favoriteAdapter.submitList(it)
    }

    private fun loadEmptyScreen() {
        rv_movies_favorite.makeGone()
        pbFavorite.makeGone()
        layoutEmpty.makeVisible()
    }

    private fun loadErrorScreen() {
        rv_movies_favorite.makeGone()
        pbFavorite.makeGone()
        layoutError.makeVisible()
    }
}