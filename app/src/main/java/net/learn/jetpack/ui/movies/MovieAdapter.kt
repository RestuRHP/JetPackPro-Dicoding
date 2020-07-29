package net.learn.jetpack.ui.movies

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
import net.learn.jetpack.ui.detail.DetailActivity
import net.learn.submission4mvvm.model.movies.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val movieSets = mutableListOf<Movie>()

    fun updateData(newPokemonSets: MutableList<Movie>) {
        movieSets.clear()
        movieSets.addAll(newPokemonSets)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(items: Movie) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movieSets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieSets[position])
    }
}