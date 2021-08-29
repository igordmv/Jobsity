package com.exercise.jobsity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exercise.jobsity.databinding.ItemShowBinding
import com.exercise.jobsity.domain.model.Show

class ShowAdapter(
    private var shows: List<Show>,
    private val onShowClicked: (Show) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShowViewHolder(
            ItemShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onShowClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val show = shows[position]
        when (holder) {
            is ShowViewHolder -> holder.bind(show)
        }
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    fun replaceItems(items: List<*>) {
        this.shows = items.filterIsInstance<Show>() as ArrayList<Show>
        notifyDataSetChanged()
    }
}

class ShowViewHolder internal constructor(
    private val binding: ItemShowBinding,
    override val onShowClicked: (Show) -> Unit
) : BaseShowViewHolder(binding = binding) {
    override fun bind(show: Show) {
        binding.show = show
        binding.viewHolder = this
    }
}