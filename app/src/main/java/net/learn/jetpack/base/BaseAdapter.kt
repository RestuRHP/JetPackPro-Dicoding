package net.learn.jetpack.base

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movie.view.*
import net.learn.jetpack.BuildConfig
import net.learn.jetpack.R
import net.learn.jetpack.data.MovieEntity
import net.learn.jetpack.ui.detail.DetailActivity


class BaseAdapter : RecyclerView.Adapter<BaseAdapter.Holder>() {

    private val listItems = ArrayList<MovieEntity>()

    fun setItem(items: List<MovieEntity>?) {
        if (items == null) return
        this.listItems.clear()
        this.listItems.addAll(items)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(items: MovieEntity) {
            with(itemView) {
                tv_title.text = items.title
                tv_language.text = items.originalLanguage
                tv_rating.text = items.voteAverage.toString()
                tv_overview.text = items.overview
                tv_release.text = items.releaseDate
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_PATH + items.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .dontAnimate()
                    .into(img_poster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID, items.id)
                    intent.putExtra(DetailActivity.EXTRA_TITLE, items.title)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listItems[position])
    }
}