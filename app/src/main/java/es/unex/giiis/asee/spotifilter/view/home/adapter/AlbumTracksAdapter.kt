package es.unex.giiis.asee.spotifilter.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.databinding.ItemAlbumTracksBinding

class AlbumTracksAdapter(
    private var tracks: List<Track>,
    private val onClick: (track: Track) -> Unit,
    private val onLongClick: (track: Track) -> Unit
): RecyclerView.Adapter<AlbumTracksAdapter.TrackViewHolder>() {

    class TrackViewHolder(
        private val binding: ItemAlbumTracksBinding,
        private val onClick: (track: Track) -> Unit,
        private val onLongClick: (track: Track) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {
            binding.itemAlbumTracksTextView1.text = track.name
            binding.itemAlbumTracksTextView2.text = track.artists
            binding.itemAlbumTracksConstraintLayout.setOnClickListener {
                onClick(track)
            }
            binding.itemAlbumTracksConstraintLayout.setOnLongClickListener {
                onLongClick(track)
                true
            }
        }

    }

    override fun getItemCount() = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemAlbumTracksBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onClick, onLongClick)
    }

    fun updateData(tracks: List<Track>) {
        this.tracks = tracks
        notifyDataSetChanged()
    }

}