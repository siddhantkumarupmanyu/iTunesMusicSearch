package sku.challenge.itunesmusicsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sku.challenge.itunesmusicsearch.repository.SearchRepository
import sku.challenge.itunesmusicsearch.vo.Track
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    // private val searchRepository: SearchRepository
) : ViewModel() {

    // todo uncomment


    // private val _tracks = MutableStateFlow<TracksResult>(TracksResult.Success(emptyList()))
    //
    // val tracks: StateFlow<TracksResult> = _tracks
    //
    // fun search(query: String) {
    //     viewModelScope.launch {
    //         _tracks.value = TracksResult.Loading
    //         val results = searchRepository.query(query)
    //         _tracks.emit(TracksResult.Success(results))
    //     }
    // }

    sealed class TracksResult {
        class Success(val tracks: List<Track>) : TracksResult()
        object Loading : TracksResult()
    }

}