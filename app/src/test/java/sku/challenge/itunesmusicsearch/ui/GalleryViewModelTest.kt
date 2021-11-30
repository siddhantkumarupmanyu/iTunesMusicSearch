package sku.challenge.itunesmusicsearch.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.yield
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import sku.challenge.itunesmusicsearch.fake.FakeRepository
import sku.challenge.itunesmusicsearch.vo.Track

@ExperimentalCoroutinesApi
class GalleryViewModelTest {


    private val tracks = listOf(
        Track(1, "overdrive", "krsna", "https://example.com/thumbnail_over_krsna.jpg"),
        Track(4, "overdrive", "prozpekt", "https://example.com/thumbnail_over_proz.jpg")
    )
    private val repository = FakeRepository()
    private val viewModel = GalleryViewModel(repository)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchTracks() = runTest {
        assertThat(
            viewModel.tracks.first(),
            IsInstanceOf(GalleryViewModel.TracksResult.Success::class.java)
        )

        assertThat(
            (viewModel.tracks.first() as GalleryViewModel.TracksResult.Success).tracks,
            `is`(emptyList())
        )

        repository.query = "overdrive"
        repository.delayBeforeReturningResult = 10L
        repository.tracks = tracks

        viewModel.search("overdrive")

        // yield() or delay so that coroutine launched by viewModelScope is Run before asserting
        // delay works since runTest uses virtual clock.
        // so it suspends the current coroutine and runs the one with less
        // more info see https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-test.html
        // and https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/README.md

        yield()
        assertThat(
            viewModel.tracks.first(),
            IsInstanceOf(GalleryViewModel.TracksResult.Loading::class.java)
        )

        // more delay relative to delayBeforeReturningResult, so that's run first
        delay(20L)

        assertThat(
            viewModel.tracks.first(),
            IsInstanceOf(GalleryViewModel.TracksResult.Success::class.java)
        )

        assertThat(
            (viewModel.tracks.first() as GalleryViewModel.TracksResult.Success).tracks,
            `is`(tracks)
        )
    }


}