package net.learn.jetpack.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.jetpack.R
import net.learn.jetpack.ui.movies.MovieFragment
import net.learn.jetpack.ui.tv.TvShowFragment


class MainActivity : AppCompatActivity() {

    var content: Fragment = MovieFragment()
    var title = "Movies"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        bottomNavigationView.setOnNavigationItemSelectedListener(navSelection)
//        if (savedInstanceState == null) {
//            bottomNavigationView.selectedItemId = R.id.navigation_movies
//        }
        initializeUI()
    }

    private val navSelection =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.navigation_movies -> {
                    content = MovieFragment()
                    title = getString(R.string.movies)
                    setActionBarTitle(title)
                    addFragment(content)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_tvshows -> {
                    content = TvShowFragment()
                    title = getString(R.string.tvShows)
                    setActionBarTitle(title)
                    addFragment(content)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar?.title = title
        }
    }

    private fun initializeUI() {
        with(main_viewpager) {
            adapter = MainPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 3
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) = Unit

                override fun onPageSelected(position: Int) {
                    bottomNavigationView.menu.getItem(position).isChecked = true
                }
            })
            bottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_movies -> currentItem = 0
                    R.id.navigation_tvshows -> currentItem = 1
//                    R.id.action_three -> currentItem = 2
                }
                true
            }
        }
    }
}
