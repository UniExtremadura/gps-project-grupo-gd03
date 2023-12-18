package es.unex.giiis.asee.spotifilter.view.home.fragment.albums

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
import es.unex.giiis.asee.spotifilter.data.model.Album
import es.unex.giiis.asee.spotifilter.databinding.FragmentAlbumsBinding
import es.unex.giiis.asee.spotifilter.refactor.Repository
import es.unex.giiis.asee.spotifilter.refactor.SpotiFilterApplication
import es.unex.giiis.asee.spotifilter.refactor.viewModel.AlbumsViewModel
import es.unex.giiis.asee.spotifilter.view.home.adapter.AlbumsAdapter

class AlbumsFragment : Fragment() {

    private var _albums: List<Album> = emptyList()
    private var _binding: FragmentAlbumsBinding? = null
    private lateinit var adapter: AlbumsAdapter
    private val binding get() = _binding!!
    private lateinit var repository: Repository
    private val viewModel: AlbumsViewModel by viewModels { AlbumsViewModel.Factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContainer = (activity?.application as SpotiFilterApplication).appContainer
        repository = appContainer.repository
    }

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
        observeViewModel()
        if (viewModel.albums.value.isNullOrEmpty()) {
            viewModel.fetchNewReleases()
        }
    }

    private fun observeViewModel() {
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            adapter.updateData(albums)
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
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
        viewModel.fetchSearchedAlbums(query)
    }

}