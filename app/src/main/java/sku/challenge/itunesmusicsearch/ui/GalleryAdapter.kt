package sku.challenge.itunesmusicsearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sku.challenge.itunesmusicsearch.databinding.ListItemBinding
import sku.challenge.itunesmusicsearch.vo.Track

class GalleryAdapter(initialTracks: List<Track> = emptyList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var tracks: List<Track> = initialTracks
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GalleryViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val track = tracks[position]
        (holder as GalleryViewHolder).bind(track)
    }

    override fun getItemCount(): Int = tracks.size

    class GalleryViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {
            binding.track = track
            binding.executePendingBindings()
        }

    }
}