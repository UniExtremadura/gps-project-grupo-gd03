package es.unex.giiis.asee.spotifilter.view.home.fragment.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.spotifilter.api.SpotifyAPIError
import es.unex.giiis.asee.spotifilter.api.SpotifyAccountsError
import es.unex.giiis.asee.spotifilter.api.getSpotifyAPIService
import es.unex.giiis.asee.spotifilter.api.getSpotifyAccountsService
import es.unex.giiis.asee.spotifilter.data.getAlbums
import es.unex.giiis.asee.spotifilter.data.getAuthorization
import es.unex.giiis.asee.spotifilter.data.model.Album
import es.unex.giiis.asee.spotifilter.databinding.FragmentAlbumsBinding
import es.unex.giiis.asee.spotifilter.view.home.adapter.AlbumsAdapter
import kotlinx.coroutines.launch

class AlbumsFragment : Fragment() {

    private var _albums: List<Album> = emptyList()
    private var _binding: FragmentAlbumsBinding? = null
    private lateinit var adapter: AlbumsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        if (_albums.isEmpty()) {
            try {
                lifecycleScope.launch {
                    _albums = fetchNewReleases()
                    adapter.updateData(_albums)
                }
            } catch (error: SpotifyAPIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            } catch (error: SpotifyAccountsError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun fetchNewReleases(): List<Album> {
        val newReleases: List<Album>
        try {
            val authorization = getSpotifyAccountsService().getSpotifyAuthorization()
                .getAuthorization()
            try {
                newReleases = getSpotifyAPIService().getSpotifyNewReleases(authorization)
                    .getAlbums()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return newReleases
    }

    private suspend fun fetchSearchedAlbums(query: String): List<Album> {
        val searchedAlbums: List<Album>
        try {
            val authorization = getSpotifyAccountsService().getSpotifyAuthorization()
                .getAuthorization()
            try {
                searchedAlbums = getSpotifyAPIService()
                    .getSpotifySearchResponse(authorization, query, "album").getAlbums()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return searchedAlbums
    }

    private fun setUpRecyclerView() {
        adapter = AlbumsAdapter(
            context = context,
            albums = _albums,
            onClick = {
                val action = AlbumsFragmentDirections
                    .actionAlbumsFragmentToAlbumDetailsFragment(it)
                findNavController().navigate(action)
            }
        )
        binding.albumsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.albumsRecyclerView.adapter = adapter
    }

    fun updateRecyclerView(query: String) {
        try {
            lifecycleScope.launch {
                _albums =
                    if (query == "") {
                        fetchNewReleases()
                    } else {
                        fetchSearchedAlbums(query)
                    }
                adapter.updateData(_albums)
            }
        } catch (error: SpotifyAPIError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    }

}