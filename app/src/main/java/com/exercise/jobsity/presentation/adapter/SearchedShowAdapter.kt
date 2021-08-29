package com.exercise.jobsity.presentation.adapter

import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exercise.jobsity.databinding.ItemSearchedShowBinding
import com.exercise.jobsity.domain.model.Show

class SearchedShowAdapter(
    private var shows: List<Show>,
    private val onShowClicked: (Show) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchedShowViewHolder(
            ItemSearchedShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onShowClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val show = shows[position]
        when (holder) {
            is SearchedShowViewHolder -> holder.bind(show)
        }
    }

    override fun getItemCount(): Int {
        return shows.size
    }
}

class SearchedShowViewHolder internal constructor(
    private val binding: ItemSearchedShowBinding,
    private val onShowClicked: (Show) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    var show: Show? = null
    fun bind(show: Show) {
        this.show = show
        binding.viewHolder = this
        binding.root.setOnClickListener { onShowClicked.invoke(show) }
    }

    fun getDescription(): Spanned? {
        show?.summary?.let {
            return Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        } ?: run { return null }
    }
}