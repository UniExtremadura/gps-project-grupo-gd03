package es.unex.giiis.asee.spotifilter.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.asee.spotifilter.data.model.Playlist
import es.unex.giiis.asee.spotifilter.databinding.ItemPlaylistsBinding

class PlaylistsAdapter(
    private var playlists: List<Playlist>,
    private val onClick: (playlist: Playlist) -> Unit
): RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder>() {

    class PlaylistViewHolder(
        private val binding: ItemPlaylistsBinding,
        private val onClick: (playlist: Playlist) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: Playlist) {
            binding.itemPlaylistsTextView1.text = playlist.name
            binding.itemPlaylistsConstraintLayout.setOnClickListener {
                onClick(playlist)
            }
        }

    }

    override fun getItemCount() = playlists.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = ItemPlaylistsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding, onClick)
    }

    fun updateData(playlists: List<Playlist>) {
        this.playlists = playlists
        notifyDataSetChanged()
    }

}