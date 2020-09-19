package net.learn.jetpack.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.tabLayout
import net.learn.jetpack.R
import net.learn.jetpack.ui.favorite.movie.MovieFavoriteFragment
import net.learn.jetpack.ui.favorite.tv.TvFavoriteFragment
import net.learn.jetpack.ui.main.MainPager

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupPager()
    }

    private fun setupPager() {
        val pagerAdapter = MainPager(supportFragmentManager)
        pagerAdapter.addFragment(MovieFavoriteFragment(), "Movie Favorite")
        pagerAdapter.addFragment(TvFavoriteFragment(), "Tv Favorite")
        favorite_viewpager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(favorite_viewpager)
    }
}