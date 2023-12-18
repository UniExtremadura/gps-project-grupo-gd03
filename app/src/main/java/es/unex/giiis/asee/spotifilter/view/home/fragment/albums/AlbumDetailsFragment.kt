package es.unex.giiis.asee.spotifilter.view.home.fragment.albums

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.databinding.FragmentAlbumDetailsBinding
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import es.unex.giiis.asee.spotifilter.refactor.viewModel.AlbumTracksViewModel
import es.unex.giiis.asee.spotifilter.view.home.adapter.AlbumTracksAdapter

class AlbumDetailsFragment : Fragment() {

    private var _binding: FragmentAlbumDetailsBinding? = null
    private var _tracks: List<Track> = emptyList()
    private lateinit var adapter: AlbumTracksAdapter
    private val args: AlbumDetailsFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private lateinit var repository: Repository
    private val viewModel: AlbumTracksViewModel by viewModels { AlbumTracksViewModel.Factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContainer = (activity?.application as SpotiFilterApplication).appContainer
        repository = appContainer.repository
    }

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
        observeViewModel()
        if (viewModel.tracks.value.isNullOrEmpty()) {
            viewModel.fetchAlbumTracks(album)
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