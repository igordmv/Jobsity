package com.exercise.jobsity.presentation.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.exercise.jobsity.R
import com.exercise.jobsity.databinding.FragmentHomeBinding
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.presentation.adapter.ShowAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var showsAdapter: ShowAdapter
    private lateinit var shows: List<Show>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }
        setupShowAdapter()
        setupObservables()
        setupListeners()
        if(::shows.isInitialized) {
            viewModel.setShows(shows)
        } else {
            viewModel.fetchShows()
        }
        return binding.root
    }

    private fun setupListeners() {
        binding.btShowSeeAll.setOnClickListener {

        }
    }

    private fun setupShowAdapter() {
        showsAdapter = ShowAdapter(emptyList(), this::clickedShow)
        binding.rvShows.adapter = showsAdapter
    }

    private fun clickedShow(show: Show) {
        val bundle = Bundle()
        bundle.putParcelable(SELECTED_SHOW, show)
        findNavController().navigate(R.id.action_homeFragment_to_showFragment, bundle)
    }

    private fun setupObservables() {
        viewModel.getLoadingLiveData().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        })

        viewModel.getShowsLiveData().observe(viewLifecycleOwner, Observer { shows ->
            if (shows.isNotEmpty()) {
                this.shows = shows
                binding.btShowSeeAll.visibility = View.VISIBLE
                showsAdapter.replaceItems(shows)
            }
        })
    }


    companion object {
        private const val SHOW_LIST = "SHOW_LIST"
        const val SELECTED_SHOW = "SELECTED_SHOW"
    }

}