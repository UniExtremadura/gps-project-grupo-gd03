package es.unex.giiis.asee.spotifilter.refactor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.asee.spotifilter.data.model.Playlist
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import kotlinx.coroutines.launch

class PlaylistsViewModel(private val repository: Repository) : ViewModel() {

    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> get() = _playlists

    fun findPlaylistsByUserId(id: Long?) {
        viewModelScope.launch {
            _playlists.postValue(repository.findPlaylistsByUserId(id))
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
                return PlaylistsViewModel(
                    (application as SpotiFilterApplication).appContainer.repository,
                ) as T
            }
        }
    }

}