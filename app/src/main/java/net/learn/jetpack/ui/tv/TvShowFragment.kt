package net.learn.jetpack.ui.tv

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
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.TvRepository
import net.learn.jetpack.ui.BaseViewState
import net.learn.jetpack.ui.tv.viewmodel.TvViewModel
import net.learn.jetpack.ui.tv.viewmodel.TvViewModelFactory
import net.learn.jetpack.utils.makeGone
import net.learn.jetpack.utils.makeVisible

class TvShowFragment : Fragment() {

    private lateinit var vm: TvViewModel
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAdapter = TvAdapter()
        rv_movies.apply {
            adapter = tvAdapter
            setHasFixedSize(true)
        }

        val factory = TvViewModelFactory(TvRepository.instance)
        vm = ViewModelProvider(this, factory).get(TvViewModel::class.java)

        initObserver()
        setupScrollListener()
    }

    private fun initObserver() {
        vm.state?.observe(viewLifecycleOwner, { state ->
            swapRefresh.setOnRefreshListener { vm.getDiscoveryTv() }
            when (state) {
                is BaseViewState.ShowLoading -> toggleLoading(true)
                is BaseViewState.HideLoading -> toggleLoading(false)
                is BaseViewState.LoadTvSuccess -> {
                    state.data?.let { showData(it) }
                    hideError()
                }
                is BaseViewState.Error -> {
                    toggleLoading(false)
                    showError()
                }
            }
        })
    }

    private fun showData(data: MutableList<TvShow>) {
        tvAdapter.updateData(data)
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