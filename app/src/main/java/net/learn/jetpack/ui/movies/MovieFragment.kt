package net.learn.jetpack.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.android.synthetic.main.display_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import net.learn.jetpack.Injection
import net.learn.jetpack.R

class MovieFragment : Fragment() {

    private lateinit var vm: MovieViewModel
    private lateinit var adapter: MovieAdapter
    private var getJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = ViewModelProvider(this, Injection.provideViewModelFactory(context))
            .get(MovieViewModel::class.java)

        initAdapter()
        getSets()
        initSwipeRefresh()
    }

    private fun initAdapter() {
        adapter = MovieAdapter()
        rv_movies.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter(adapter),
            footer = MovieLoadStateAdapter(adapter)
        )
        lifecycleScope.launchWhenCreated {
            adapter.addLoadStateListener {
                swapRefresh.isRefreshing = it.refresh is LoadState.Loading
                tv_loadmore_message.isVisible = it.append is LoadState.Loading
                val errorState = it.append as? LoadState.Error
                    ?: it.source.prepend as? LoadState.Error
                    ?: it.append as? LoadState.Error
                    ?: it.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.prepend }
                .filter { it.prepend is LoadState.Loading }
                .collect { rv_movies.smoothScrollToPosition(0) }
        }
        rv_movies.setHasFixedSize(true)
    }

    private fun getSets() {
        getJob?.cancel()
        getJob = lifecycleScope.launch {
            vm.currentItem?.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initSwipeRefresh() {
        swapRefresh.setOnRefreshListener { adapter.refresh() }
    }

}