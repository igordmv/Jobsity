package com.exercise.jobsity.presentation.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.exercise.jobsity.domain.model.Show

/**
 * BaseViewHolder class used for dataBinding at item_show.xml
 */
open class BaseShowViewHolder internal constructor(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    open val onShowClicked: (Show) -> Unit = {}

    open fun bind(show: Show) {}

}