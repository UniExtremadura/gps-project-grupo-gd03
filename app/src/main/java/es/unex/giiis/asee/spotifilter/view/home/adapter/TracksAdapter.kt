package es.unex.giiis.asee.spotifilter.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.databinding.ItemTracksBinding

class TracksAdapter(
    private val context: Context?,
    private var tracks: List<Track>,
    private val onClick: (track: Track) -> Unit,
    private val onLongClick: (track: Track) -> Unit,
): RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    class TrackViewHolder(
        private val context: Context?,
        private val binding: ItemTracksBinding,
        private val onClick: (track: Track) -> Unit,
        private val onLongClick: (track: Track) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {
            context?.let {
                Glide.with(it).load(track.image)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.itemTracksImageView)
            }
            binding.itemTracksTextView1.text = track.name
            binding.itemTracksTextView2.text = track.artists
            binding.itemTracksTextView3.text = track.album
            binding.itemTracksConstraintLayout.setOnClickListener {
                onClick(track)
            }
            binding.itemTracksConstraintLayout.setOnLongClickListener {
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
        val binding = ItemTracksBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(context, binding, onClick, onLongClick)
    }

    fun updateData(tracks: List<Track>) {
        this.tracks = tracks
        notifyDataSetChanged()
    }

}