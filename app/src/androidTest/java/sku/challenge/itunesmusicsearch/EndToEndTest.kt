package sku.challenge.itunesmusicsearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sku.challenge.itunesmusicsearch.di.BaseUrl
import sku.challenge.itunesmusicsearch.di.ConstantsModule
import sku.challenge.itunesmusicsearch.test_utils.OkHttp3IdlingResource
import sku.challenge.itunesmusicsearch.test_utils.RecyclerViewMatcher
import sku.challenge.itunesmusicsearch.test_utils.enqueueResponse
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(ConstantsModule::class)
@HiltAndroidTest
class EndToEndTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @BindValue
    val baseUrl: BaseUrl = BaseUrl("http://127.0.0.1:8080")

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun startServer() {
        hiltRule.inject()

        setupRetrofitClient()
    }

    @Test
    fun searchSong() {
        mockWebServer.start(8080)

        val mockResponse = MockResponse()
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        enqueueResponse()

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.search_view)).perform(
            click(),
            typeText("overdrive"),
            pressImeActionButton()
        )

        assertPing()
        assertRequestsAreMade()

        Thread.sleep(100)

        onView(listMatcher().atPosition(1)).check(matches(hasDescendant(withText("Katy Rose"))))
        onView(listMatcher().atPosition(3)).check(matches(hasDescendant(withText("Jaytech"))))

        // remove internet
        // search for some other song
        // assert no items

        mockWebServer.shutdown()

        onView(withId(R.id.search_view)).perform(
            click(),
            clearText(),
            typeText("untitled"),
            pressImeActionButton()
        )

        Thread.sleep(100)

        onView(withId(R.id.empty_list_placeholder)).check(matches(isDisplayed()))

        // search overdrive again
        // assert again

        onView(withId(R.id.search_view)).perform(
            click(),
            clearText(),
            typeText("overdrive"),
            pressImeActionButton()
        )

        Thread.sleep(200)

        // since we ain't sorting the list again.
        // so items are displaced
        matchIfItemIsInList(listOf("Katy Rose", "Jaytech"))

        Thread.sleep(500)

        activityScenario.close()
    }

    private fun matchIfItemIsInList(items: List<String>) {
        val mutableItems = items.toMutableList()
        var matchedIndex = -1

        for (i in 0 until 50) {
            if (mutableItems.isEmpty()) {
                break
            }

            for ((index, item) in mutableItems.withIndex()) {
                try {
                    onView(withId(R.id.grid_view))
                        .perform(ScrollAction(i))

                    onView(listMatcher().atPosition(i)).check(matches(hasDescendant(withText(item))))

                    matchedIndex = index
                    break
                } catch (e: AssertionError) {
                }
            }
            if (matchedIndex != -1) {
                mutableItems.removeAt(matchedIndex)
            }
            matchedIndex = -1
        }

        assertThat("list does not contain the items", mutableItems.size, `is`(0))
    }

    private fun assertPing() {
        val request = mockWebServer.takeRequest(2, TimeUnit.SECONDS)
        assertThat(request.method, Is.`is`("HEAD"))
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

    // https://stackoverflow.com/a/55990445
    class ScrollAction(private val position: Int) : ViewAction {
        override fun getDescription(): String {
            return "scroll RecyclerView to bottom"
        }

        override fun getConstraints(): Matcher<View> {
            return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
        }

        override fun perform(uiController: UiController?, view: View?) {
            val recyclerView = view as RecyclerView
            val itemCount = recyclerView.adapter?.itemCount
            recyclerView.scrollToPosition(position)
            uiController?.loopMainThreadUntilIdle()
        }
    }

}