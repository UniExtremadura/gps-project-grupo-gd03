package es.unex.giiis.asee.spotifilter.view.home.fragment.playlists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.spotifilter.data.model.Playlist
import es.unex.giiis.asee.spotifilter.data.model.User
import es.unex.giiis.asee.spotifilter.database.SpotiFilterDatabase
import es.unex.giiis.asee.spotifilter.databinding.FragmentPlaylistsBinding
import es.unex.giiis.asee.spotifilter.view.home.UserProvider
import es.unex.giiis.asee.spotifilter.view.home.adapter.PlaylistsAdapter
import kotlinx.coroutines.launch

class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null
    private var _playlists: List<Playlist> = emptyList()
    private lateinit var adapter: PlaylistsAdapter
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
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
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

    private suspend fun createPlaylist() {
        val name = binding.playlistsPlainText.text.toString()
        if (name.isBlank()) {
            Toast.makeText(context, "Invalid playlist's name", Toast.LENGTH_SHORT).show()
        } else if (database.playlistDao().findByNameAndUserId(name, user.id) != null) {
            Toast.makeText(context, "Playlist '$name' already exists", Toast.LENGTH_SHORT)
                .show()
        } else {
            val playlist = Playlist(null, name, user.id!!)
            database.playlistDao().insert(playlist)
            Toast.makeText(context, "Playlist '$name' created", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpListeners() {
        binding.playlistsButton.setOnClickListener {
            lifecycleScope.launch {
                createPlaylist()
                updateRecyclerView()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = PlaylistsAdapter(
            playlists = _playlists,
            onClick = {
                val action = PlaylistsFragmentDirections
                    .actionPlaylistsFragmentToPlaylistDetailsFragment(it)
                findNavController().navigate(action)
            }
        )
        binding.playlistsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.playlistsRecyclerView.adapter = adapter
    }

    private suspend fun updateRecyclerView() {
        _playlists = database.playlistDao().findByUserId(user.id)
        adapter.updateData(_playlists)
    }

}