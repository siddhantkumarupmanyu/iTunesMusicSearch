package sku.challenge.itunesmusicsearch.fake

import kotlinx.coroutines.delay
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.hamcrest.core.IsEqual
import sku.challenge.itunesmusicsearch.repository.SearchRepository
import sku.challenge.itunesmusicsearch.vo.Track

// just because mockito does not allow suspend function while stubbing
// can use mockito-koltin but, just another dependency for only this simple case
class FakeRepository : SearchRepository {

    var query: String = ""
    var delayBeforeReturningResult: Long = 0
    var tracks: List<Track> = emptyList()

    override suspend fun query(query: String): List<Track> {
        MatcherAssert.assertThat(query, Is.`is`(IsEqual.equalTo(this.query)))

        delay(delayBeforeReturningResult)

        return tracks
    }

}