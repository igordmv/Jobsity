package com.exercise.jobsity.presentation.fragment.show

import android.R.attr.defaultValue
import android.R.attr.key
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.exercise.jobsity.databinding.FragmentShowBinding
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.presentation.fragment.home.HomeFragment.Companion.SELECTED_SHOW
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShowFragment : Fragment() {

    private lateinit var selectedShow: Show
    private lateinit var binding: FragmentShowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(inflater, container, false)
        context ?: return binding.root

        selectedShow = arguments?.get(SELECTED_SHOW) as Show
        setupListeners()
        setupView(selectedShow)
        return binding.root
    }

    private fun setupView(selectedShow: Show) {
        Glide.with(binding.ivBanner)
            .load(selectedShow.image)
            .into(binding.ivBanner)
        binding.tvShowName.text = selectedShow.name
        binding.tvShowDescription.text = Html.fromHtml(selectedShow.summary, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}