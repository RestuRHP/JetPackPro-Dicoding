package net.learn.jetpack.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import net.learn.jetpack.R
import net.learn.jetpack.utils.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    fun loadData() {
        onView(
            allOf(
                withId(R.id.rv_movies),
                isDisplayed()
            )
        )
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.navigation_tvShows)).perform(click())
        onView(
            allOf(
                withId(R.id.rv_movies),
                isDisplayed()
            )
        )
    }

    @Test
    fun loadDetail() {
        onView(
            allOf(
                withId(R.id.rv_movies),
                isDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.backdrop_image)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack())
//        onView(withId(R.id.navigation_tvShows)).perform(click())
//        onView(
//            allOf(
//                withId(R.id.rv_movies),
//                isDisplayed()
//            )
//        ).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                0,
//                click()
//            )
//        )
//        onView(isRoot()).perform(ViewActions.pressBack())
    }
}