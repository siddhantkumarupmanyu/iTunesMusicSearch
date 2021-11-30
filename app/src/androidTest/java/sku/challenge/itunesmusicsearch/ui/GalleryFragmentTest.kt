package sku.challenge.itunesmusicsearch.ui

import android.view.InputDevice
import android.view.MotionEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sku.challenge.itunesmusicsearch.R
import sku.challenge.itunesmusicsearch.test_utils.DataBindingIdlingResourceRule
import sku.challenge.itunesmusicsearch.test_utils.launchFragmentInHiltContainer


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class GalleryFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @JvmField
    @Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    @Before
    fun setUp() {
        // Populate @Inject fields in test class
        hiltRule.inject()

        launchFragmentInHiltContainer<GalleryFragment> {
            dataBindingIdlingResourceRule.monitorFragment(this)
        }
    }

    @Test
    fun clearSearchViewText_WhenClearDrawableIsClicked() {
        onView(withId(R.id.search_view)).perform(click(), typeText("song name"))
        onView(withId(R.id.search_view)).check(matches(withText("song name")))

        onView(withId(R.id.search_view)).perform(clickPercent(0.9f, 0.5f))
        onView(withId(R.id.search_view)).check(matches(withText("")))
    }

    @Test
    fun showProgressBar_WhenLoading() {
        onView(withId(R.id.search_view)).perform(
            click(),
            typeText("song name"),
            pressImeActionButton()
        )

        onView(withId(R.id.progress_indicator)).check(matches(isDisplayed()))
    }

    @Ignore
    @Test
    fun showEmptyImageView_WhenTracksAreEmpty() {

    }

    // https://stackoverflow.com/a/58841245
    @Suppress("SameParameterValue")
    private fun clickPercent(percentageX: Float, percentageY: Float): ViewAction {
        return GeneralClickAction(
            Tap.SINGLE,
            CoordinatesProvider { view ->
                val screenPos = IntArray(2)
                view.getLocationOnScreen(screenPos)
                val w = view.width
                val h = view.height

                val x = w * percentageX
                val y = h * percentageY

                val screenX = screenPos[0] + x
                val screenY = screenPos[1] + y

                floatArrayOf(screenX, screenY)
            },
            Press.FINGER,
            InputDevice.SOURCE_MOUSE,
            MotionEvent.BUTTON_PRIMARY
        )
    }


}