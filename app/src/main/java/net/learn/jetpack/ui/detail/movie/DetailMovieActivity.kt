package net.learn.jetpack.ui.detail.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.activity_detail.*
import net.learn.jetpack.BuildConfig
import net.learn.jetpack.R
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.detail.DetailMovieRepository
import net.learn.jetpack.ui.detail.movie.viewmodel.DetailMovieState
import net.learn.jetpack.ui.detail.movie.viewmodel.DetailMovieViewModel
import net.learn.jetpack.ui.detail.movie.viewmodel.DetailMovieViewModelFactory
import net.learn.jetpack.utils.makeGone
import net.learn.jetpack.utils.makeVisible
import net.learn.jetpack.utils.showSnackBar

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var vm: DetailMovieViewModel
    private lateinit var detailMovieAdapter: MovieDetailAdapter
    private var isMovieFavorite = false
    private var data: Movie? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie
        lifecycleScope.launchWhenCreated {
            movie.id?.let { vm.getSimilarMovie(it) }
        }

        initData()
        setUpDetail()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailMovieAdapter = MovieDetailAdapter()
        rv_similar.apply {
            adapter = detailMovieAdapter
            setHasFixedSize(true)
        }
        val factory = DetailMovieViewModelFactory(DetailMovieRepository.instance)
        vm = ViewModelProvider(this, factory).get(DetailMovieViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bindData()

        iniObserver()
        data = intent.getParcelableExtra(EXTRA_MOVIE)
    }

    private fun initData() {
        lifecycleScope.launchWhenCreated {
            data?.id?.let { vm.isFavoriteMovie(it) }
        }
    }

    private fun setUpDetail() {
        ivTheaterFavorite.setOnClickListener { clickAction(isMovieFavorite) }
    }

    private fun clickAction(isFavorite: Boolean) {
        lifecycleScope.launchWhenCreated {
            if (isFavorite) {
                data?.let {
                    it.id?.let { it1 -> vm.removeMovieFromFavorite(it1) }
                }
            } else {
                data?.let { it.id?.let { it1 -> vm.setMovieToFavorite(it1) } }
            }
        }
    }

    private fun bindData() {
        val movieItems = intent.getParcelableExtra(EXTRA_MOVIE) as Movie
        toolbar.title = movieItems.title
        collapsing.title = movieItems.title

        with(movieItems) {
            tv_release.text = releaseDate
            tv_rating.text = voteAverage.toString()
            tv_language.text = originalLanguage
            tv_overview.text = overview
            Glide.with(this@DetailMovieActivity)
                .load(BuildConfig.BACKDROP_PATH + backdropPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                )
                .listener(
                    GlidePalette.with(BuildConfig.BACKDROP_PATH + posterPath)
                        .use(BitmapPalette.Profile.VIBRANT)
                        .intoBackground(collapsing)
                        .crossfade(true)
                )
                .dontAnimate()
                .into(backdrop_image)

        }
    }

    private fun iniObserver() {
        vm.state.observe(this, Observer { state ->
            when (state) {
                is DetailMovieState.ShowLoading -> {
                    pBDetail.makeVisible()
                }
                is DetailMovieState.HideLoading -> {
                    pBDetail.makeGone()
                }
                is DetailMovieState.LoadScreenError -> {
                    pBDetail.makeGone()
                    showError(true)
                }
                is DetailMovieState.LoadSimilarMovieSuccess -> {
                    state.data?.let { showData(it) }
                    showError(false)
                }
                is DetailMovieState.IsFavoriteTheater -> {
                    isMovieFavorite = state.status
                    setupFavoriteButton(isMovieFavorite)
                }
                is DetailMovieState.SuccessAddFavorite -> {
                    isMovieFavorite = true
                    showSnackBar(detail_activity, getString(R.string.success_add_favorite))
                    setupFavoriteButton(isMovieFavorite)
                }
                is DetailMovieState.FailedAddFavorite -> {
                    showSnackBar(detail_activity, getString(R.string.failed_add_favorite))
                }
                is DetailMovieState.FailedRemoveFavorite -> {
                    showSnackBar(detail_activity, getString(R.string.failed_remove_favorite))
                }
                is DetailMovieState.SuccessRemoveFavorite -> {
                    isMovieFavorite = false
                    setupFavoriteButton(isMovieFavorite)
                    showSnackBar(detail_activity, getString(R.string.success_remove_favorite))
                }
            }
        })
    }

    private fun showError(state: Boolean) {
        if (state) layout_warning.makeVisible() else layout_warning.makeGone()
    }

    private fun showData(data: MutableList<Movie>?) {
        detailMovieAdapter.updateData(data)
    }

    private fun setupFavoriteButton(isFavorite: Boolean) {
        when {
            isFavorite -> {
                ivTheaterFavorite.apply {
                    setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_favorite,
                            null
                        )
                    )
                }
            }
            else -> {
                ivTheaterFavorite.apply {
                    setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_unfavorite,
                            null
                        )
                    )
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}