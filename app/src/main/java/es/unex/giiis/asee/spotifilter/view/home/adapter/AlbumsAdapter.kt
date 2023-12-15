package es.unex.giiis.asee.spotifilter.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.data.model.Album
import es.unex.giiis.asee.spotifilter.databinding.ItemAlbumsBinding

class AlbumsAdapter(
    private val context: Context?,
    private var albums: List<Album>,
    private val onClick: (album: Album) -> Unit
): RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(
        private val context: Context?,
        private val binding: ItemAlbumsBinding,
        private val onClick: (album: Album) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            context?.let {
                Glide.with(it).load(album.image)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.itemAlbumsImageView)
            }
            binding.itemAlbumsTextView1.text = album.name
            binding.itemAlbumsTextView2.text = album.artists
            binding.itemAlbumsConstraintLayout.setOnClickListener {
                onClick(album)
            }
        }

    }

    override fun getItemCount() = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(context, binding, onClick)
    }

    fun updateData(albums: List<Album>) {
        this.albums = albums
        notifyDataSetChanged()
    }

}