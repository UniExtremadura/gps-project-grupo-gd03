package es.unex.giiis.asee.spotifilter.refactor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.asee.spotifilter.api.SpotifyAPIError
import es.unex.giiis.asee.spotifilter.api.SpotifyAccountsError
import es.unex.giiis.asee.spotifilter.data.model.Album
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import kotlinx.coroutines.launch

class AlbumTracksViewModel(private val repository: Repository) : ViewModel() {

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> get() = _tracks

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchAlbumTracks(album: Album) {
        viewModelScope.launch {
            try {
                _tracks.postValue(repository.fetchAlbumTracks(album))
            } catch (error: SpotifyAPIError) {
                _error.value = error.message
            } catch (error: SpotifyAccountsError) {
                _error.value = error.message
            }
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
                return AlbumTracksViewModel(
                    (application as SpotiFilterApplication).appContainer.repository,
                ) as T
            }
        }
    }

}