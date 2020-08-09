package net.learn.jetpack.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.learn.jetpack.ui.movies.MovieFragment
import net.learn.jetpack.ui.tv.TvShowFragment

class MainPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
//            else -> PersonListFragment()
        }
    }

    override fun getCount() = 2
}