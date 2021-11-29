package sku.challenge.itunesmusicsearch

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sku.challenge.itunesmusicsearch.test_utils.OkHttp3IdlingResource
import sku.challenge.itunesmusicsearch.test_utils.RecyclerViewMatcher
import sku.challenge.itunesmusicsearch.test_utils.enqueueResponse
import java.util.concurrent.TimeUnit

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EndToEndTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @BindValue
    val okHttpClient = OkHttpClient()

    @Before
    fun startServer() {
        hiltRule.inject()

        setupRetrofitClient()

        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun searchSong() {
        enqueueResponse()

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.search_view)).perform(
            click(),
            typeText("overdrive"),
            pressImeActionButton()
        )

        assertRequestsAreMade()

        onView(listMatcher().atPosition(1)).check(matches(hasDescendant(withText("Katy Rose"))))
        onView(listMatcher().atPosition(3)).check(matches(hasDescendant(withText("Jaytech"))))

        activityScenario.close()
    }

    private fun assertRequestsAreMade() {
        assertRequest("/search?term=overdrive&media=music&entity=song")
    }

    private fun assertRequest(path: String) {
        val request = mockWebServer.takeRequest(2, TimeUnit.SECONDS)
        assertThat(request.path, `is`(path))
    }

    private fun enqueueResponse() {
        mockWebServer.enqueueResponse("overdrive.txt")
    }

    private fun setupRetrofitClient() {
        val resource = OkHttp3IdlingResource.create("okHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.grid_view)
    }

}