package es.unex.giiis.asee.spotifilter.view.home.fragment.tracks

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
import es.unex.giiis.asee.spotifilter.data.getAuthorization
import es.unex.giiis.asee.spotifilter.data.getTracks
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.databinding.FragmentTracksBinding
import es.unex.giiis.asee.spotifilter.view.home.adapter.TracksAdapter
import kotlinx.coroutines.launch

class TracksFragment : Fragment() {

    private var _binding: FragmentTracksBinding? = null
    private var _tracks: List<Track> = emptyList()
    private lateinit var adapter: TracksAdapter
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        if (_tracks.isEmpty()) {
            try {
                lifecycleScope.launch {
                    _tracks = fetchTopGlobal()
                    adapter.updateData(_tracks)
                }
            } catch (error: SpotifyAPIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            } catch (error: SpotifyAccountsError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun fetchSearchedTracks(query: String): List<Track> {
        val searchedTracks: List<Track>
        try {
            val authorization = getSpotifyAccountsService().getSpotifyAuthorization()
                .getAuthorization()
            try {
                searchedTracks = getSpotifyAPIService()
                    .getSpotifySearchResponse(authorization, query, "track").getTracks()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return searchedTracks
    }

    private suspend fun fetchTopGlobal(): List<Track> {
        val topGlobal: List<Track>
        try {
            val authorization = getSpotifyAccountsService().getSpotifyAuthorization()
                .getAuthorization()
            try {
                topGlobal = getSpotifyAPIService()
                    .getSpotifyPlaylist(authorization, "37i9dQZEVXbMDoHDwVN2tF").getTracks()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return topGlobal
    }

    private fun setUpRecyclerView() {
        adapter = TracksAdapter(
            context = context,
            tracks = _tracks,
            onClick = {
                val action = TracksFragmentDirections
                    .actionTracksFragmentToTrackDetailsFragment(it)
                findNavController().navigate(action)
            },
            onLongClick = {
                val action = TracksFragmentDirections
                    .actionTracksFragmentToAddTrackToPlaylistFragment(it)
                findNavController().navigate(action)
            }
        )
        binding.tracksRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.tracksRecyclerView.adapter = adapter
    }

    fun updateRecyclerView(query: String) {
        try {
            lifecycleScope.launch {
                _tracks =
                    if (query == "") {
                        fetchTopGlobal()
                    } else {
                        fetchSearchedTracks(query)
                    }
                adapter.updateData(_tracks)
            }
        } catch (error: SpotifyAPIError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    }

}