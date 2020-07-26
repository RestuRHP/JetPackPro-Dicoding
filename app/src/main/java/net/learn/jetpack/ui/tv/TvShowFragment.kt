package net.learn.jetpack.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.display_fragment.*
import net.learn.jetpack.R
import net.learn.jetpack.base.BaseAdapter
import net.learn.jetpack.base.BaseViewModel

class TvShowFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[BaseViewModel::class.java]
        val items = viewModel.getTvShows()

        val adapter = BaseAdapter()
        adapter.setItem(items)

        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.setHasFixedSize(true)
        rv_movies.adapter = adapter
    }

}