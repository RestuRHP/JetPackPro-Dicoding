package net.learn.jetpack.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_movie.view.img_poster
import kotlinx.android.synthetic.main.item_movie.view.tv_title
import net.learn.jetpack.BuildConfig
import net.learn.jetpack.R
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.ui.detail.tv.DetailTvActivity

class FavoriteAdapter : PagedListAdapter<Movie, FavoriteAdapter.ViewHolder>(callback) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(items: Movie) {
            with(itemView) {
                tv_title.text = items.title
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_PATH + items.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                    )
                    .listener(
                        GlidePalette.with(BuildConfig.POSTER_PATH + items.posterPath)
                            .use(BitmapPalette.Profile.VIBRANT)
                            .intoBackground(itemView.item_poster_palette)
                            .crossfade(true)
                    )
                    .dontAnimate()
                    .into(img_poster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvActivity::class.java)
                    intent.putExtra(DetailTvActivity.EXTRA_TV, items)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position) as Movie
        holder.bind(movie)
    }
}