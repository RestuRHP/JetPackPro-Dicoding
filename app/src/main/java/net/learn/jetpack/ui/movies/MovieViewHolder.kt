package net.learn.jetpack.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item_movie.view.*
import net.learn.jetpack.BuildConfig
import net.learn.jetpack.R
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.ui.detail.DetailActivity

class MovieViewHolder(view: View) : ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.tv_title)
    private val poster: ImageView = view.findViewById(R.id.img_poster)
    private val release: TextView = view.findViewById(R.id.tv_release)
    private val overview: TextView = view.findViewById(R.id.tv_overview)
    private val rating: TextView = view.findViewById(R.id.tv_rating)
    private val language: TextView = view.findViewById(R.id.tv_language)

    private var movie: Movie? = null

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, movie?.id)
            intent.putExtra(DetailActivity.EXTRA_TITLE, movie?.title)
            view.context.startActivity(intent)
        }
    }

    fun bind(movie: Movie?) {
        if (movie != null) showMovie(movie)
    }

    private fun showMovie(movie: Movie) {
        this.movie = movie
        title.text = movie.title
        release.text = movie.releaseDate
        overview.text = movie.overview
        rating.text = movie.voteAverage.toString()
        language.text = movie.originalLanguage
        movie.posterPath.let {
            Glide.with(itemView.context)
                .load(BuildConfig.POSTER_PATH + it)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .listener(
                    GlidePalette.with(BuildConfig.POSTER_PATH + it)
                        .use(BitmapPalette.Profile.VIBRANT_DARK)
                        .intoBackground(itemView.background_layout)
                        .crossfade(true)
                )
                .into(poster)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
            return MovieViewHolder(view)
        }
    }

}