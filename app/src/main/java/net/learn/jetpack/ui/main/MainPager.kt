package net.learn.jetpack.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPager(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentList: MutableList<Fragment> = mutableListOf()
    private val fragmentTitleList: MutableList<String> = mutableListOf()

    override fun getCount(): Int = fragmentList.size

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? = fragmentTitleList[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}