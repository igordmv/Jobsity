package com.exercise.jobsity.presentation.fragment.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.exercise.jobsity.databinding.FragmentEpisodeBinding
import com.exercise.jobsity.domain.model.Episode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private lateinit var episode: Episode
    private val viewModel: EpisodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }

        episode = arguments?.get(SELECTED_EPISODE) as Episode
        viewModel.updateEpisode(episode)
        updateBanner()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateBanner() {
        episode.image?.let {
            Glide.with(binding.ivBanner)
                .load(it)
                .into(binding.ivBanner)
        }
    }

    companion object {
        const val SELECTED_EPISODE = "SELECTED_EPISODE"
    }
}