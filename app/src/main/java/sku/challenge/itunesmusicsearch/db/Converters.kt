package sku.challenge.itunesmusicsearch.db

import androidx.room.TypeConverter
import sku.challenge.itunesmusicsearch.vo.Track
import kotlin.math.max

// class Converters {
//
//     // list of track -> comma separated string
//     @TypeConverter
//     fun fromListOfTracksToCommaSeparatedString(results: List<Track>): String {
//         val string = StringBuilder()
//         results.forEach {
//             string.append("${it.trackId}, ")
//         }
//         string.setLength(max(string.length - 2, 0))
//         return string.toString()
//     }
//
//     // comma separated string to -> list of long
//     @TypeConverter
//     fun fromCommaSeparatedStringToIntList(string: String): List<Track> {
//     }
// }