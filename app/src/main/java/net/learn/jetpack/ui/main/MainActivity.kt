package net.learn.jetpack.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.jetpack.R
import net.learn.jetpack.ui.movies.MovieFragment
import net.learn.jetpack.ui.tv.TvShowFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupPager()
    }

    private fun setupPager() {
        val pagerAdapter = MainPager(supportFragmentManager)
        pagerAdapter.addFragment(MovieFragment(), "Movie")
        pagerAdapter.addFragment(TvShowFragment(), "Tv Show")
        main_viewpager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(main_viewpager)
    }

}
