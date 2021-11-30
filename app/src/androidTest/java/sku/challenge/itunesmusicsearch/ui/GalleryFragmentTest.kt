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
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sku.challenge.itunesmusicsearch.R
import sku.challenge.itunesmusicsearch.fake.FakeRepository
import sku.challenge.itunesmusicsearch.test_utils.DataBindingIdlingResourceRule
import sku.challenge.itunesmusicsearch.test_utils.launchFragmentInHiltContainer
import sku.challenge.itunesmusicsearch.vo.Track


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class GalleryFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @JvmField
    @Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()


    private val tracks = listOf(
        Track(1, "overdrive", "krsna", "https://example.com/thumbnail_over_krsna.jpg"),
        Track(4, "overdrive", "prozpekt", "https://example.com/thumbnail_over_proz.jpg")
    )

    @BindValue
    val repository = FakeRepository()

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
    fun showProgressBar_WhenLoading() = runTest {
        repository.query = "overdrive"
        repository.delayBeforeReturningResult = 10L
        repository.tracks = tracks

        onView(withId(R.id.progress_indicator)).check(matches(not(isDisplayed())))

        onView(withId(R.id.search_view)).perform(
            click(),
            typeText("overdrive"),
            pressImeActionButton()
        )

        onView(withId(R.id.progress_indicator)).check(matches(isDisplayed()))

        delay(20L)

        onView(withId(R.id.progress_indicator)).check(matches(not(isDisplayed())))
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