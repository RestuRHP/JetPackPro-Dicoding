package net.learn.submission_1.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import net.learn.submission_1.BuildConfig
import net.learn.submission_1.R
import net.learn.submission_1.base.BaseViewModel
import net.learn.submission_1.data.MovieEntity


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[BaseViewModel::class.java]
        val entityId: String? = intent.getStringExtra(EXTRA_ID)
        viewModel.setId(entityId)
        val entity = viewModel.getDetails()
        showDetail(entity)

        supportActionBar?.title = intent.getStringExtra(EXTRA_TITLE)
        supportActionBar?.elevation = 0f

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showDetail(entity: MovieEntity?) {
        tv_release.text = entity?.releaseDate
        tv_rating.text = entity?.voteAverage.toString()
        tv_language.text = entity?.originalLanguage
        tv_overview.text = entity?.overview
        Glide.with(this)
            .load(BuildConfig.BACKDROP_PATH + entity?.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .dontAnimate()
            .into(backdrop_image)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}