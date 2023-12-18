package es.unex.giiis.asee.spotifilter.refactor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.asee.spotifilter.api.SpotifyAPIError
import es.unex.giiis.asee.spotifilter.api.SpotifyAccountsError
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import kotlinx.coroutines.launch

class TracksViewModel(private val repository: Repository) : ViewModel() {

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> get() = _tracks

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchTopGlobal() {
        viewModelScope.launch {
            try {
                _tracks.postValue(repository.fetchTopGlobal())
            } catch (error: SpotifyAPIError) {
                _error.value = error.message
            } catch (error: SpotifyAccountsError) {
                _error.value = error.message
            }
        }
    }

    fun fetchSearchedTracks(query: String) {
        viewModelScope.launch {
            try {
                if (query == "") {
                    _tracks.postValue(repository.fetchTopGlobal())
                } else {
                    _tracks.postValue(repository.fetchSearchedTracks(query))
                }
            } catch (error: SpotifyAPIError) {
                _error.postValue(error.message)
            } catch (error: SpotifyAccountsError) {
                _error.postValue(error.message)
            }
        }
    }

    fun findTracksByPlaylistIdOrderByReleaseDate(id: Long?, artists: String) {
        viewModelScope.launch {
            _tracks.postValue(repository.findTracksByPlaylistIdOrderByReleaseDate(id, artists))
        }
    }

    fun findTracksByPlaylistIdOrderByPopularity(id: Long?, artists: String) {
        viewModelScope.launch {
            _tracks.postValue(repository.findTracksByPlaylistIdOrderByPopularity(id, artists))
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return TracksViewModel(
                    (application as SpotiFilterApplication).appContainer.repository,
                ) as T
            }
        }
    }

}