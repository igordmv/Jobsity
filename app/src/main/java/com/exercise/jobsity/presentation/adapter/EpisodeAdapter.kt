package com.exercise.jobsity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exercise.jobsity.databinding.ItemEpisodeBinding
import com.exercise.jobsity.domain.model.Episode

class EpisodeAdapter(
    private var episodes: List<Episode>,
    private val onEpisodeClicked: (Episode) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EpisodeViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onEpisodeClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val episode = episodes[position]
        when (holder) {
            is EpisodeViewHolder -> holder.bind(episode)
        }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    fun replaceItems(items: List<*>) {
        this.episodes = items.filterIsInstance<Episode>() as ArrayList<Episode>
        notifyDataSetChanged()
    }
}

class EpisodeViewHolder internal constructor(
    private val binding: ItemEpisodeBinding,
    private val onEpisodeClicked: (Episode) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(episode: Episode) {
        binding.tvEpisodeName.text = episode.name
        binding.tvEpisodeNumber.text = "${episode.number}"
        binding.tvEpisodeAirDate.text = episode.airDate
        binding.root.setOnClickListener { onEpisodeClicked.invoke(episode) }
    }
}