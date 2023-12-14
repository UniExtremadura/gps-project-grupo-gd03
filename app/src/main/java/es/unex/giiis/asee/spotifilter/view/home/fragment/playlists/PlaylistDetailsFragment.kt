package es.unex.giiis.asee.spotifilter.view.home.fragment.playlists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.api.SpotifyAPIError
import es.unex.giiis.asee.spotifilter.api.SpotifyAccountsError
import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.database.SpotiFilterDatabase
import es.unex.giiis.asee.spotifilter.databinding.FragmentPlaylistDetailsBinding
import es.unex.giiis.asee.spotifilter.view.home.adapter.TracksAdapter
import kotlinx.coroutines.launch

class PlaylistDetailsFragment : Fragment() {

    private var _binding: FragmentPlaylistDetailsBinding? = null
    private var _tracks: List<Track> = emptyList()
    private lateinit var adapter: TracksAdapter
    private val args: PlaylistDetailsFragmentArgs by navArgs()
    private var artists: String = ""
    private val binding get() = _binding!!
    private lateinit var database: SpotiFilterDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        database = SpotiFilterDatabase.getInstance(context)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpRecyclerView()
        if (_tracks.isEmpty()) {
            try {
                lifecycleScope.launch {
                    _tracks = database.trackDao()
                        .findByPlaylistIdOrderByReleaseDate(args.playlist.id, artists)
                    adapter.updateData(_tracks)
                }
            } catch (error: SpotifyAPIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            } catch (error: SpotifyAccountsError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun removeTrackFromPlaylist(track: Track) {
        val playlistTrack = PlaylistTrack(args.playlist.id!!, track.id)
        database.playlistTrackDao().delete(playlistTrack)
        if (database.playlistTrackDao().findByTrackId(track.id).isEmpty()) {
            database.trackDao().delete(track)
        }
    }

    private fun setUpRecyclerView() {
        adapter = TracksAdapter(
            context = context,
            tracks = _tracks,
            onClick = {
                val action = PlaylistDetailsFragmentDirections
                    .actionPlaylistDetailsFragmentToTrackDetailsFragment(it)
                findNavController().navigate(action)
            },
            onLongClick = {
                lifecycleScope.launch {
                    removeTrackFromPlaylist(it)
                    updateRecyclerView()
                }
            }
        )
        binding.playlistDetailsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.playlistDetailsRecyclerView.adapter = adapter
    }

    private fun setUpUI() {
        binding.playlistDetailsTextView1.text = args.playlist.name
        binding.playlistDetailsEditText.addTextChangedListener { editable ->
            artists = editable.toString()
            updateRecyclerView()
        }
        binding.playlistDetailsRadioButton1.isChecked = true
        binding.playlistDetailsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.playlistDetailsRadioButton1, R.id.playlistDetailsRadioButton2
                -> updateRecyclerView()
            }
        }
    }

    private fun updateRecyclerView() {
        val playlist = args.playlist
        try {
            lifecycleScope.launch {
                _tracks = database.trackDao()
                    .findByPlaylistIdOrderByReleaseDate(playlist.id, artists)
                adapter.updateData(_tracks)
            }
        } catch (error: SpotifyAPIError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    }

}