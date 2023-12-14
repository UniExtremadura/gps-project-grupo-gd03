package es.unex.giiis.asee.spotifilter.view.home.fragment.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.api.SpotifyAPIError
import es.unex.giiis.asee.spotifilter.api.SpotifyAccountsError
import es.unex.giiis.asee.spotifilter.api.getSpotifyAPIService
import es.unex.giiis.asee.spotifilter.api.getSpotifyAccountsService
import es.unex.giiis.asee.spotifilter.data.getAuthorization
import es.unex.giiis.asee.spotifilter.data.getTracks
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.databinding.FragmentAlbumDetailsBinding
import es.unex.giiis.asee.spotifilter.view.home.adapter.AlbumTracksAdapter
import kotlinx.coroutines.launch

class AlbumDetailsFragment : Fragment() {

    private var _binding: FragmentAlbumDetailsBinding? = null
    private var _tracks: List<Track> = emptyList()
    private lateinit var adapter: AlbumTracksAdapter
    private val args: AlbumDetailsFragmentArgs by navArgs()
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val album = args.album
        context?.let {
            Glide.with(it).load(album.image).placeholder(R.drawable.placeholder)
                .into(binding.albumDetailsImageView)
        }
        binding.albumDetailsTextView1.text = album.name
        binding.albumDetailsTextView2.text = album.artists
        setUpRecyclerView()
        if (_tracks.isEmpty()) {
            try {
                lifecycleScope.launch {
                    _tracks = fetchAlbumTracks()
                    adapter.updateData(_tracks)
                }
            } catch (error: SpotifyAPIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            } catch (error: SpotifyAccountsError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun fetchAlbumTracks(): List<Track> {
        val albumTracks: List<Track>
        try {
            val authorization = getSpotifyAccountsService().getSpotifyAuthorization()
                .getAuthorization()
            try {
                albumTracks = getSpotifyAPIService()
                    .getSpotifyAlbumTracks(authorization, args.album.id).getTracks()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return albumTracks
    }

    private fun setUpRecyclerView() {
        val album = args.album
        adapter = AlbumTracksAdapter(
            tracks = _tracks,
            onClick = {
                it.album = album.name
                it.image = album.image
                it.releaseDate = album.releaseDate
                val action = AlbumDetailsFragmentDirections
                    .actionAlbumDetailsFragmentToTrackDetailsFragment(it)
                findNavController().navigate(action)
            },
            onLongClick = {
                it.album = album.name
                it.image = album.image
                it.releaseDate = album.releaseDate
                val action = AlbumDetailsFragmentDirections
                    .actionAlbumDetailsFragmentToAddTrackToPlaylistFragment(it)
                findNavController().navigate(action)
            }
        )
        binding.albumDetailsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.albumDetailsRecyclerView.adapter = adapter
    }

}