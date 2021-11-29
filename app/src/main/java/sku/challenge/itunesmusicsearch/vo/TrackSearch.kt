package sku.challenge.itunesmusicsearch.vo

data class TrackSearch(
    val resultCount: Int,
    val results: List<Track>
) {
    lateinit var query: String

    companion object {
        val EMPTY_TRACKER_SEARCH = TrackSearch(0, emptyList())
    }
}
