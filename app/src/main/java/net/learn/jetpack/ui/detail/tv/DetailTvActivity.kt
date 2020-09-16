package net.learn.jetpack.ui.detail.tv

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
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.detail.DetailTvRepository
import net.learn.jetpack.ui.detail.tv.viewmodel.DetailTvState
import net.learn.jetpack.ui.detail.tv.viewmodel.DetailTvViewModel
import net.learn.jetpack.ui.detail.tv.viewmodel.DetailTvViewModelFactory
import net.learn.jetpack.utils.makeGone
import net.learn.jetpack.utils.makeVisible
import net.learn.jetpack.utils.showSnackBar

class DetailTvActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV = "extra_Tv"
    }

    private lateinit var vm: DetailTvViewModel
    private lateinit var detailTvAdapter: TvDetailAdapter
    private var isTvFavorite = false
    private var data: TvShow? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val movie = intent.getParcelableExtra(EXTRA_TV) as TvShow
        lifecycleScope.launchWhenCreated {
            movie.id?.let { vm.getSimilarTv(it) }
        }

        initData()
        setUpDetail()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailTvAdapter = TvDetailAdapter()
        rv_similar.apply {
            adapter = detailTvAdapter
            setHasFixedSize(true)
        }
        val factory = DetailTvViewModelFactory(DetailTvRepository.instance)
        vm = ViewModelProvider(this, factory).get(DetailTvViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bindData()

        iniObserver()
        data = intent.getParcelableExtra(EXTRA_TV)
    }

    private fun initData() {
        lifecycleScope.launchWhenCreated {
            data?.id?.let { vm.isFavorite(it) }
        }
    }

    private fun setUpDetail() {
        ivTheaterFavorite.setOnClickListener { clickAction(isTvFavorite) }
    }

    private fun clickAction(isFavorite: Boolean) {
        lifecycleScope.launchWhenCreated {
            if (isFavorite) {
                data?.let {
                    it.id?.let { it1 -> vm.removeTvFromFavorite(it1) }
                }
            } else {
                data?.let { it.id?.let { it1 -> vm.setTvToFavorite(it1) } }
            }
        }
    }

    private fun bindData() {
        val tvItems = intent.getParcelableExtra(EXTRA_TV) as TvShow
        toolbar.title = tvItems.title
        collapsing.title = tvItems.title

        with(tvItems) {
            tv_release.text = releaseDate
            tv_rating.text = voteAverage.toString()
            tv_language.text = originalLanguage
            tv_overview.text = overview
            Glide.with(this@DetailTvActivity)
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
                is DetailTvState.ShowLoading -> {
                    pBDetail.makeVisible()
                }
                is DetailTvState.HideLoading -> {
                    pBDetail.makeGone()
                }
                is DetailTvState.LoadScreenError -> {
                    pBDetail.makeGone()
                    showError(true)
                }
                is DetailTvState.LoadSimilarSuccess -> {
                    state.data?.let { showData(it) }
                    showError(false)
                }
                is DetailTvState.IsFavoriteTheater -> {
                    isTvFavorite = state.status
                    setupFavoriteButton(isTvFavorite)
                }
                is DetailTvState.SuccessAddFavorite -> {
                    isTvFavorite = true
                    showSnackBar(detail_activity, getString(R.string.success_add_favorite))
                    setupFavoriteButton(isTvFavorite)
                }
                is DetailTvState.FailedAddFavorite -> {
                    showSnackBar(detail_activity, getString(R.string.failed_add_favorite))
                }
                is DetailTvState.FailedRemoveFavorite -> {
                    showSnackBar(detail_activity, getString(R.string.failed_remove_favorite))
                }
                is DetailTvState.SuccessRemoveFavorite -> {
                    isTvFavorite = false
                    setupFavoriteButton(isTvFavorite)
                    showSnackBar(detail_activity, getString(R.string.success_remove_favorite))
                }
            }
        })
    }

    private fun showError(state: Boolean) {
        if (state) layout_warning.makeVisible() else layout_warning.makeGone()
    }

    private fun showData(data: MutableList<TvShow>) {
        detailTvAdapter.updateData(data)
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