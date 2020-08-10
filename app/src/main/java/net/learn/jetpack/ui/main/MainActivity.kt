package net.learn.jetpack.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.jetpack.R
import net.learn.jetpack.ui.movies.MovieFragment


class MainActivity : AppCompatActivity() {

    var content: Fragment = MovieFragment()
    var title = "Movies"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar?.title = title
        }
    }

    private fun initializeUI() {
        with(main_viewpager) {
            adapter = MainPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 2
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) = Unit

                override fun onPageSelected(position: Int) {
//                    bottomNavigationView.menu.getItem(position).isChecked = true
                    when (position) {
                        0 -> {
                            currentItem = 0
                            title = getString(R.string.movies)
                            setActionBarTitle(title)
                        }
                        1 -> {
                            currentItem = 1
                            title = getString(R.string.tvShows)
                            setActionBarTitle(title)
                        }
                    }
                }

            })
            bottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_movies -> {
                        currentItem = 0
                        title = getString(R.string.movies)
                        setActionBarTitle(title)
                    }
                    R.id.navigation_tvShows -> {
                        currentItem = 1
                        title = getString(R.string.tvShows)
                        setActionBarTitle(title)
                    }
//                    R.id.action_three -> currentItem = 2
                }
                true
            }
        }
    }
}
