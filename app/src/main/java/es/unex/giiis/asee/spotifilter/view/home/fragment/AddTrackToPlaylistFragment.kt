package es.unex.giiis.asee.spotifilter.view.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.spotifilter.data.model.Playlist
import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack
import es.unex.giiis.asee.spotifilter.data.model.User
import es.unex.giiis.asee.spotifilter.databinding.FragmentAddTrackToPlaylistBinding
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import es.unex.giiis.asee.spotifilter.refactor.viewModel.PlaylistsViewModel
import es.unex.giiis.asee.spotifilter.view.home.adapter.PlaylistsAdapter
import es.unex.giiis.asee.spotifilter.view.home.UserProvider
import kotlinx.coroutines.launch

class AddTrackToPlaylistFragment : Fragment() {

    private var _binding: FragmentAddTrackToPlaylistBinding? = null
    private var _playlists: List<Playlist> = emptyList()
    private lateinit var adapter: PlaylistsAdapter
    private val args: AddTrackToPlaylistFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private lateinit var repository: Repository
    private lateinit var user: User
    private val viewModel: PlaylistsViewModel by viewModels { PlaylistsViewModel.Factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContainer = (activity?.application as SpotiFilterApplication).appContainer
        repository = appContainer.repository
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
        observeViewModel()
        if (viewModel.playlists.value.isNullOrEmpty()) {
            viewModel.findPlaylistsByUserId(user.id)
        }
    }

    private suspend fun addTrackToNewPlaylist() {
        val name = binding.addTrackToPlaylistPlainText.text.toString()
        val track = args.track
        if (name.isBlank()) {
            Toast.makeText(context, "Invalid playlist's name", Toast.LENGTH_SHORT)
                .show()
        } else if (repository.findPlaylistByNameAndUserId(name, user.id) != null) {
            Toast.makeText(context, "Playlist '$name' already exists", Toast.LENGTH_SHORT)
                .show()
        } else {
            val playlist = Playlist(null, name, user.id!!)
            playlist.id = repository.insertPlaylist(playlist)
            val playlistTrack = PlaylistTrack(playlist.id!!, track.id)
            repository.insertPlaylistTrack(playlistTrack)
            if (repository.findTrackById(track.id) == null) {
                repository.insertTrack(track)
            }
            updateRecyclerView()
            Toast.makeText(context, "Playlist '$name' created with '${track.name}'",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            adapter.updateData(playlists)
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
                    repository.addTrackToPlaylist(requireContext(), it, args.track)
                }
            }
        )
        binding.addTrackToPlaylistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.addTrackToPlaylistRecyclerView.adapter = adapter
    }

    private fun updateRecyclerView() {
        viewModel.findPlaylistsByUserId(user.id)
    }

}