package com.exercise.jobsity.presentation.fragment.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.exercise.jobsity.R
import com.exercise.jobsity.databinding.FragmentShowBinding
import com.exercise.jobsity.domain.model.Episode
import com.exercise.jobsity.domain.model.Season
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.presentation.adapter.EpisodeAdapter
import com.exercise.jobsity.presentation.extensions.runOnUI
import com.exercise.jobsity.presentation.fragment.episode.EpisodeFragment.Companion.SELECTED_EPISODE
import com.exercise.jobsity.presentation.fragment.home.HomeFragment.Companion.SELECTED_SHOW
import com.exercise.jobsity.presentation.widget.Alerts
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShowFragment : Fragment() {

    private lateinit var selectedShow: Show
    private lateinit var binding: FragmentShowBinding
    private lateinit var seasons: List<Season>
    private lateinit var episodes: List<Episode>
    private lateinit var episodesAdapter: EpisodeAdapter
    private val viewModel: ShowViewModel by viewModels()
    private var isFavorite: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }

        selectedShow = arguments?.get(SELECTED_SHOW) as Show
        setupListeners()
        setupObservers()
        setupEpisodeAdapter()
        viewModel.setSelectedShow(selectedShow)
        return binding.root
    }

    private fun setupEpisodeAdapter() {
        episodesAdapter = EpisodeAdapter(emptyList(), this::clickedEpisode)
        binding.rvSeasonEpisodes.adapter = episodesAdapter
    }

    private fun clickedEpisode(episode: Episode) {
        val bundle = Bundle()
        bundle.putParcelable(SELECTED_EPISODE, episode)
        findNavController().navigate(R.id.action_showFragment_to_episodeFragment, bundle)
    }

    private fun setupObservers() {
        viewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer { message ->
            if(message.isNotBlank()) {
                Alerts.snackInfo(binding.root, message)
            }
        })
        viewModel.getSeasonLiveData().observe(viewLifecycleOwner, Observer { seasons ->
            if (seasons.isNotEmpty()) {
                this.seasons = seasons
                var seasonList = mutableListOf<String>()
                seasons.forEach { season ->
                    seasonList.add("Season ${season.number}")
                }
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        seasonList
                    )
                binding.spinnerSeasons.adapter = adapter
                binding.spinnerSeasons.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            viewModel.seasonSelected(seasons[position])
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }
                    }
            }
        })

        viewModel.getEpisodeLiveData().observe(viewLifecycleOwner, Observer { episodes ->
            if (episodes.isNotEmpty()) {
                this.episodes = episodes
                episodesAdapter.replaceItems(episodes)
            }
        })

        viewModel.getIsFavoriteObservable().observe(viewLifecycleOwner, Observer { isFavorite ->
            this.isFavorite = isFavorite
            runOnUI {
                if (isFavorite) {
                    binding.ivFavoriteImage.setBackgroundResource(R.drawable.favorite_pressed)
                } else {
                    binding.ivFavoriteImage.setBackgroundResource(R.drawable.favorite_unpressed)
                }
            }
        })
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivFavoriteImage.setOnClickListener {
            isFavorite?.let { isFavorite ->
                if (isFavorite) {
                    viewModel.removeFavorite(selectedShow)
                } else {
                    viewModel.setFavorite(selectedShow)
                }
            }
        }
    }
}