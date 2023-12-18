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
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import kotlinx.coroutines.launch

class AlbumsViewModel(private val repository: Repository) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchNewReleases() {
        viewModelScope.launch {
            try {
                _albums.postValue(repository.fetchNewReleases())
            } catch (error: SpotifyAPIError) {
                _error.value = error.message
            } catch (error: SpotifyAccountsError) {
                _error.value = error.message
            }
        }
    }

    fun fetchSearchedAlbums(query: String) {
        viewModelScope.launch {
            try {
                if (query == "") {
                    _albums.postValue(repository.fetchNewReleases())
                } else {
                    _albums.postValue(repository.fetchSearchedAlbums(query))
                }
            } catch (error: SpotifyAPIError) {
                _error.postValue(error.message)
            } catch (error: SpotifyAccountsError) {
                _error.postValue(error.message)
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
                return AlbumsViewModel(
                    (application as SpotiFilterApplication).appContainer.repository,
                ) as T
            }
        }
    }

}