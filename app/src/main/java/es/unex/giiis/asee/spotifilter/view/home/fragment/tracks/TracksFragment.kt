package es.unex.giiis.asee.spotifilter.view.home.fragment.tracks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.databinding.FragmentTracksBinding
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import es.unex.giiis.asee.spotifilter.refactor.viewModel.TracksViewModel
import es.unex.giiis.asee.spotifilter.view.home.adapter.TracksAdapter

class TracksFragment : Fragment() {

    private var _binding: FragmentTracksBinding? = null
    private var _tracks: List<Track> = emptyList()
    private lateinit var adapter: TracksAdapter
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
        observeViewModel()
        if (viewModel.tracks.value.isNullOrEmpty()) {
            viewModel.fetchTopGlobal()
        }
    }

    private fun observeViewModel() {
        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            adapter.updateData(tracks)
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
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
        viewModel.fetchSearchedTracks(query)
    }

}