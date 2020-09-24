package net.learn.jetpack.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.display_fragment.*
import net.learn.jetpack.R
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.MovieRepository
import net.learn.jetpack.ui.movies.viewmodel.MovieViewModel
import net.learn.jetpack.ui.movies.viewmodel.MovieViewModelFactory
import net.learn.jetpack.utils.makeGone
import net.learn.jetpack.utils.makeVisible

class MovieFragment : Fragment() {

    private lateinit var vm: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter()
        rv_movies.apply {
            adapter = movieAdapter
            setHasFixedSize(true)
        }
        val factory = MovieViewModelFactory(MovieRepository.instance)
        vm = ViewModelProvider(this, factory).get(MovieViewModel::class.java)

        initObserver()
        setupScrollListener()
    }

    private fun initObserver() {
        vm.state?.observe(viewLifecycleOwner, { state ->
            swapRefresh.setOnRefreshListener { vm.getDiscoveryMovie() }
            when (state) {
                is net.learn.jetpack.ui.BaseViewState.ShowLoading -> toggleLoading(true)
                is net.learn.jetpack.ui.BaseViewState.HideLoading -> toggleLoading(false)
                is net.learn.jetpack.ui.BaseViewState.LoadMovieSuccess -> {
                    state.data?.let { showData(it) }
                    hideError()
                }
                is net.learn.jetpack.ui.BaseViewState.Error -> {
                    toggleLoading(false)
                    showError()
                }
            }
        })
    }

    private fun showData(data: MutableList<Movie>) {
        movieAdapter.updateData(data)
    }

    private fun toggleLoading(loading: Boolean) {
        swapRefresh.isRefreshing = loading
    }

    private fun showError() = layoutError.makeVisible()

    private fun hideError() = layoutError.makeGone()

    private fun setupScrollListener() {
        rv_movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                layoutManager?.let {
                    val visibleItemCount = it.childCount
                    val totalItemCount = it.itemCount
                    val firstVisibleItemPosition = when (layoutManager) {
                        is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
                        is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
                        else -> return
                    }
                    vm.listScrolled(visibleItemCount, firstVisibleItemPosition, totalItemCount)
                }
            }
        })
    }

}