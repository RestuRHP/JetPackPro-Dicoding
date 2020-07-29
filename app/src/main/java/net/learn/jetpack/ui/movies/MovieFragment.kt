package net.learn.jetpack.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.display_fragment.*
import net.learn.jetpack.R
import net.learn.jetpack.data.repository.MovieRepository
import net.learn.submission4mvvm.model.movies.Movie

class MovieFragment : Fragment() {

    private lateinit var vm: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter()
        rv_movies.adapter = adapter
        rv_movies.setHasFixedSize(true)
        rv_movies.layoutManager = LinearLayoutManager(this.context)

        val factory =
            MovieListFactory(MovieRepository.instance)
        vm = ViewModelProvider(this, factory).get(MovieViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@MovieFragment::handleState))
            swapRefresh.setOnRefreshListener { getSets() }
        }
    }

    private fun handleState(viewState: MovieViewState?) {
        viewState?.let {
            toggleLoading(it.loading)
            it.data?.let { data -> showData(data) }
            it.error?.let { error -> showError(error) }
        }
    }

    private fun showData(data: MutableList<Movie>) {
        adapter.updateData(data)

    }

    private fun showError(error: Exception) {

    }

    private fun toggleLoading(loading: Boolean) {
        swapRefresh.isRefreshing = loading
    }

}