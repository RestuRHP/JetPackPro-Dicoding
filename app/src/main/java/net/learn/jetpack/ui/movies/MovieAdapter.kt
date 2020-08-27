package net.learn.jetpack.ui.movies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.learn.jetpack.model.movies.Movie

class MovieAdapter : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
//            (holder as MovieViewHolder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }
//    private val movieSets = mutableListOf<Movie>()
//
//    fun updateData(sets: MutableList<Movie>) {
//        movieSets.clear()
//        movieSets.addAll(sets)
//        notifyDataSetChanged()
//    }
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(items: Movie) {
//            with(itemView) {
//                tv_title.text = items.title
//                tv_language.text = items.originalLanguage
//                tv_rating.text = items.voteAverage.toString()
//                tv_overview.text = items.overview
//                tv_release.text = items.releaseDate
//                Glide.with(itemView.context)
//                    .load(BuildConfig.POSTER_PATH + items.posterPath)
//                    .apply(
//                        RequestOptions.placeholderOf(R.drawable.ic_loading)
//                            .error(R.drawable.ic_error)
//                    )
//                    .dontAnimate()
//                    .into(img_poster)
//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailActivity::class.java)
//                    intent.putExtra(DetailActivity.EXTRA_ID, items.id)
//                    intent.putExtra(DetailActivity.EXTRA_TITLE, items.title)
//                    itemView.context.startActivity(intent)
//                }
//            }
//
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int = movieSets.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(movieSets[position])
//    }
}