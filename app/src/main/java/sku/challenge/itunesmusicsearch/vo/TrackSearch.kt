package sku.challenge.itunesmusicsearch.vo

data class TrackSearch(
    val resultCount: Int,
    val results: List<Track>
) {
    lateinit var query: String

    companion object {
        val EMPTY_TRACKER_SEARCH = TrackSearch(0, emptyList())
    }


    override fun hashCode(): Int {
        var result = resultCount
        result = 31 * result + results.hashCode()
        result = 31 * result + query.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrackSearch

        if (resultCount != other.resultCount) return false
        if (results != other.results) return false
        if (query != other.query) return false

        return true
    }
}
