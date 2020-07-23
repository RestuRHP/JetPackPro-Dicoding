package net.learn.submission_1.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.learn.submission_1.R
import net.learn.submission_1.ui.movies.MovieFragment
import net.learn.submission_1.ui.tv.TvShowFragment


class MainActivity : AppCompatActivity() {

    var content: Fragment = MovieFragment()
    var title = "Movies"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(navSelection)
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.navigation_movies
        }
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
}
