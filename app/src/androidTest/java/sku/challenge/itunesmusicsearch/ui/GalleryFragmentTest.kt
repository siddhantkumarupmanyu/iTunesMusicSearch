package sku.challenge.itunesmusicsearch.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
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
    fun testSpike() {
        onView(withId(R.id.search_view)).perform(click())

        Thread.sleep(2000)
    }

}