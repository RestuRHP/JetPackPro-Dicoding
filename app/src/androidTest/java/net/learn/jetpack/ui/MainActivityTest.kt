package net.learn.jetpack.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import net.learn.jetpack.R
import net.learn.jetpack.ui.main.MainActivity
import net.learn.jetpack.utils.Dummy.Dummy
import org.junit.Rule
import org.junit.Test


class MainActivityTest {
    private val dummyMovie = Dummy.generateDummnyMovies()
    private val dummyTv = Dummy.generateDummyTvShows()


    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovie.size
            )
        )
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.navigation_tvshows)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTv.size
            )
        )
    }

//    @Test
//    fun loadDetail() {
//        onView(withId(R.id.rv_movies)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                0,
//                click()
//            )
//        )
//        onView(
//            allOf(
//                instanceOf(TextView::class.java),
//                withParent(withResourceName("action_bar"))
//            )
//        ).check(matches(isDisplayed()))
//        onView(
//            allOf(
//                instanceOf(TextView::class.java),
//                withParent(withResourceName("action_bar"))
//            )
//        )
//            .check(matches(withText(dummyMovie[0].title)))
//        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_release)).check(matches(withText(dummyMovie[0].releaseDate)))
//        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_rating)).check(matches(withText(dummyMovie[0].voteAverage.toString())))
//        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_language)).check(matches(withText(dummyMovie[0].originalLanguage)))
//        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_overview)).check(matches(withText(dummyMovie[0].overview)))
//        onView(withId(R.id.backdrop_image)).check(matches(isDisplayed()))
//        onView(isRoot()).perform(ViewActions.pressBack())
//    }

}