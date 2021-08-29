package com.exercise.jobsity.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.exercise.jobsity.R
import com.exercise.jobsity.databinding.ItemShowBinding
import com.exercise.jobsity.domain.model.Show

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
        val show = shows[position]
        Glide.with(binding.ivBanner)
            .load(show.image)
            .placeholder(R.drawable.show_placeholder)
            .into(binding.ivBanner)
        binding.tvShowName.text = show.name
        binding.root.setOnClickListener { onShowClicked.invoke(show) }
        return binding.root

    }

    fun loadMore(items: List<Show>) {
        this.shows.addAll(items)
        notifyDataSetChanged()
    }
}