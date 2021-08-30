package com.exercise.jobsity.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.exercise.jobsity.databinding.ItemShowBinding
import com.exercise.jobsity.domain.model.Show

/**
 * Show grid adapter for loading multiple shows with pagination
 */
class GridShowAdapter(
    private var shows: MutableList<Show>,
    private val onShowClicked: (Show) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = shows.size
    override fun getItem(position: Int): Any = shows[position]
    override fun getItemId(position: Int): Long = 0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemShowBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )
        ShowViewHolder(binding, onShowClicked).bind(shows[position])
        return binding.root
    }

    fun loadMore(items: List<Show>) {
        this.shows.addAll(items)
        notifyDataSetChanged()
    }
}