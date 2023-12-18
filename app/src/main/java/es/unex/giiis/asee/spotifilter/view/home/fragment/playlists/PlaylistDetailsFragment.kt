package es.unex.giiis.asee.spotifilter.view.home.fragment.playlists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.databinding.FragmentPlaylistDetailsBinding
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import es.unex.giiis.asee.spotifilter.refactor.viewModel.TracksViewModel
import es.unex.giiis.asee.spotifilter.view.home.adapter.TracksAdapter
import kotlinx.coroutines.launch

class PlaylistDetailsFragment : Fragment() {

    private var _binding: FragmentPlaylistDetailsBinding? = null
    private var _tracks: List<Track> = emptyList()
    private lateinit var adapter: TracksAdapter
    private val args: PlaylistDetailsFragmentArgs by navArgs()
    private var artists: String = ""
    private val binding get() = _binding!!
    private lateinit var repository: Repository
    private val viewModel: TracksViewModel by viewModels { TracksViewModel.Factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContainer = (activity?.application as SpotiFilterApplication).appContainer
        repository = appContainer.repository
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
        observeViewModel()
        if (viewModel.tracks.value.isNullOrEmpty()) {
            viewModel.findTracksByPlaylistIdOrderByReleaseDate(args.playlist.id, artists)
        }
    }

    private fun observeViewModel() {
        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            adapter.updateData(tracks)
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
                    repository.removeTrackFromPlaylist(it, args.playlist)
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
        if (binding.playlistDetailsRadioButton1.isChecked) {
            viewModel.findTracksByPlaylistIdOrderByReleaseDate(args.playlist.id, artists)
        } else if (binding.playlistDetailsRadioButton2.isChecked) {
            viewModel.findTracksByPlaylistIdOrderByPopularity(playlist.id, artists)
        }
    }

}