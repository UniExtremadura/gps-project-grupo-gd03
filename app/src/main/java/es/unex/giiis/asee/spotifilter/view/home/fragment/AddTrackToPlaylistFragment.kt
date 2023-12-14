package es.unex.giiis.asee.spotifilter.view.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.spotifilter.data.model.Playlist
import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack
import es.unex.giiis.asee.spotifilter.data.model.User
import es.unex.giiis.asee.spotifilter.database.SpotiFilterDatabase
import es.unex.giiis.asee.spotifilter.databinding.FragmentAddTrackToPlaylistBinding
import es.unex.giiis.asee.spotifilter.view.home.adapter.PlaylistsAdapter
import es.unex.giiis.asee.spotifilter.view.home.UserProvider
import kotlinx.coroutines.launch

class AddTrackToPlaylistFragment : Fragment() {

    private var _binding: FragmentAddTrackToPlaylistBinding? = null
    private var _playlists: List<Playlist> = emptyList()
    private lateinit var adapter: PlaylistsAdapter
    private val args: AddTrackToPlaylistFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private lateinit var database: SpotiFilterDatabase
    private lateinit var user: User

    override fun onAttach(context: Context) {
        super.onAttach(context)
        database = SpotiFilterDatabase.getInstance(context)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTrackToPlaylistBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpRecyclerView()
        user = (activity as UserProvider).getUser()
        if (_playlists.isEmpty()) {
            lifecycleScope.launch {
                _playlists = database.playlistDao().findByUserId(user.id)
                adapter.updateData(_playlists)
            }
        }
    }

    private suspend fun addTrackToNewPlaylist() {
        val name = binding.addTrackToPlaylistPlainText.text.toString()
        val track = args.track
        if (name.isBlank()) {
            Toast.makeText(context, "Invalid playlist's name", Toast.LENGTH_SHORT)
                .show()
        } else if (database.playlistDao().findByNameAndUserId(name, user.id) != null) {
            Toast.makeText(context, "Playlist '$name' already exists", Toast.LENGTH_SHORT)
                .show()
        } else {
            val playlist = Playlist(null, name, user.id!!)
            playlist.id = database.playlistDao().insert(playlist)
            val playlistTrack = PlaylistTrack(playlist.id!!, track.id)
            database.playlistTrackDao().insert(playlistTrack)
            if (database.trackDao().findById(track.id) == null) {
                database.trackDao().insert(track)
            }
            updateRecyclerView()
            Toast.makeText(context, "Playlist '$name' created with '${track.name}'",
                Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun addTrackToPlaylist(playlist: Playlist) {
        val track = args.track
        if (database.playlistTrackDao()
                .findByPlaylistIdAndTrackId(playlist.id!!, track.id) != null
        ) {
            Toast.makeText(context, "'${playlist.name}' already contains '${track.name}'",
                Toast.LENGTH_SHORT).show()
        } else {
            val playlistTrack = PlaylistTrack(playlist.id!!, track.id)
            database.playlistTrackDao().insert(playlistTrack)
            if (database.trackDao().findById(track.id) == null) {
                database.trackDao().insert(track)
            }
            Toast.makeText(context, "'${track.name}' added to '${playlist.name}'",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpListeners() {
        binding.addTrackToPlaylistButton.setOnClickListener {
            lifecycleScope.launch {
                addTrackToNewPlaylist()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = PlaylistsAdapter(
            playlists = _playlists,
            onClick = {
                lifecycleScope.launch {
                    addTrackToPlaylist(it)
                }
            }
        )
        binding.addTrackToPlaylistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.addTrackToPlaylistRecyclerView.adapter = adapter
    }

    private fun updateRecyclerView() {
        lifecycleScope.launch {
            _playlists = database.playlistDao().findByUserId(user.id)
            adapter.updateData(_playlists)
        }
    }

}