package sku.challenge.itunesmusicsearch.vo

data class TrackSearch(
    val resultCount: Int,
    val results: List<Track>
) {
    lateinit var query: String
}
