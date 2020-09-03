package net.learn.jetpack.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import net.learn.jetpack.R
import net.learn.jetpack.databinding.NetworkStateItemBinding

class NetworkStateItemViewHolder(parent: ViewGroup, private val retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.network_state_item, parent, false
        )
    ) {
    private val binding = NetworkStateItemBinding.bind(itemView)
    private val errorMsg = binding.errorMsg
    private val loadMore = binding.tvLoadmoreMessage
    private val retry = binding.retryButton
        .also {
            it.setOnClickListener { retryCallback() }
        }

    fun bindTo(loadState: LoadState) {
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? LoadState.Error)?.error?.message
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }
        errorMsg.isVisible = loadState !is LoadState.Loading
    }
}