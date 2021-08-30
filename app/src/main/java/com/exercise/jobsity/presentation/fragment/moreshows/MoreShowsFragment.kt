package com.exercise.jobsity.presentation.fragment.moreshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.exercise.jobsity.R
import com.exercise.jobsity.databinding.FragmentMoreShowsBinding
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.presentation.adapter.GridShowAdapter
import com.exercise.jobsity.presentation.fragment.home.HomeFragment
import com.exercise.jobsity.presentation.widget.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreShowsFragment : Fragment() {

    private var isRestored: Boolean = false
    private lateinit var binding: FragmentMoreShowsBinding
    private val viewModel: MoreShowsViewModel by viewModels()
    private var shows: MutableList<Show> = mutableListOf()
    private lateinit var showsAdapter: GridShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreShowsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }

        if (shows.isNotEmpty()) {
            showsAdapter = GridShowAdapter(shows as MutableList<Show>, this::clickedShow)
            binding.gvShows.adapter = showsAdapter
        } else {
            viewModel.fetchShows()
        }
        setupListeners()
        setupObservers()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        isRestored = false
    }

    override fun onResume() {
        super.onResume()
        isRestored = true
    }

    private fun clickedShow(show: Show) {
        val bundle = Bundle()
        bundle.putParcelable(HomeFragment.SELECTED_SHOW, show)
        findNavController().navigate(R.id.action_moreShowsFragment_to_showFragment, bundle)
    }

    private fun setupObservers() {
        viewModel.getErrorObservable().observe(viewLifecycleOwner, Observer { message ->
            if(message.isNotBlank()){
                Alerts.snackInfo(binding.root, message)
            }

        })
        viewModel.getLoadingLiveData().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        })

        viewModel.getShowsLiveData().observe(viewLifecycleOwner, Observer { shows ->
            if (shows.isNotEmpty() && isRestored) {
                this.shows.addAll(shows)
                if (!::showsAdapter.isInitialized) {
                    showsAdapter = GridShowAdapter(shows as MutableList<Show>, this::clickedShow)
                    binding.gvShows.adapter = showsAdapter
                } else {
                    showsAdapter.loadMore(shows)
                }
            }
        })
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.gvShows.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount != 0 && viewModel.canPaginate()) {
                    viewModel.isAlreadyRequesting = true
                    viewModel.getMoreShows()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, state: Int) {
                //TODO: SOLID FAIL =(
            }
        })
    }

}