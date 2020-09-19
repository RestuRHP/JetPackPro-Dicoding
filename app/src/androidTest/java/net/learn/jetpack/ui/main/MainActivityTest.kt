package net.learn.jetpack.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.learn.jetpack.R
import net.learn.jetpack.utils.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testMainTabLayout() {
        onView(withId(R.id.tabLayout)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun testSwipePagerMainLayoutSwipeLeftRight() {
        onView(withId(R.id.main_viewpager)).check(matches(isDisplayed()))
        onView(withId(R.id.main_viewpager)).perform(swipeLeft())
        onView(withId(R.id.main_viewpager)).perform(swipeRight())
    }

    @Test
    fun testRecyclerDataOnMainLayoutSwipeDown() {
        onView(allOf(withId(R.id.rv_movies), isDisplayed()))
        onView(allOf(withId(R.id.rv_movies), isDisplayed())).perform(swipeUp())
        onView(allOf(withId(R.id.rv_movies), isDisplayed())).perform(swipeDown())
    }

    @Test
    fun testRecyclerDataOnMainLayoutClick() {
        onView(allOf(withId(R.id.rv_movies), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.ivTheaterFavorite)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun testMenuFavorite() {
        onView(withId(R.id.mnFavorite)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun testFavoriteTabLayout() {
        onView(withId(R.id.mnFavorite)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.tabLayout)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun testSwipePagerFavoriteLayoutSwipeLeftRight() {
        onView(withId(R.id.mnFavorite)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_viewpager)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_viewpager)).perform(swipeLeft())
        onView(withId(R.id.favorite_viewpager)).perform(swipeRight())
    }

    @Test
    fun testFavoriteItemShow() {
        onView(withId(R.id.mnFavorite)).perform(click()).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_movies_favorite), isDisplayed()))
    }

    @Test
    fun testRecyclerDataOnFavoriteLayoutClick() {
        onView(withId(R.id.mnFavorite)).perform(click()).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_movies_favorite), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(isRoot()).perform(pressBack())
    }

}