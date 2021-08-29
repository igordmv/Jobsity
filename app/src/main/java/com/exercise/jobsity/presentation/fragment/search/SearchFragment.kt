package com.exercise.jobsity.presentation.fragment.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.exercise.jobsity.R
import com.exercise.jobsity.databinding.FragmentSearchBinding
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.presentation.adapter.SearchedShowAdapter
import com.exercise.jobsity.presentation.extensions.runOnUI
import com.exercise.jobsity.presentation.fragment.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener, TextWatcher {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchedShowAdapter
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchView: SearchView
    private lateinit var searchViewText: EditText
    private var query: String? = null
    private var timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        context ?: return binding.root
        setHasOptionsMenu(true)
        binding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }

        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        viewModel.getShowsLiveData().observe(viewLifecycleOwner, Observer { shows ->
            adapter = SearchedShowAdapter(shows, this::clickedShow)
            binding.rvSearchedShows.adapter = adapter
        })
    }

    private fun clickedShow(show: Show) {
        val bundle = Bundle()
        bundle.putParcelable(HomeFragment.SELECTED_SHOW, show)
        findNavController().navigate(R.id.action_navigation_search_to_showFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.searchToolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_searchview, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        configureSearchView()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun afterTextChanged(text: Editable?) {
        query = text.toString()
        timer.cancel()
        timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    runOnUI {
                        if (text.toString().length >= AUTO_SEARCH_TEXT_MIN_SIZE) {
                            viewModel.search(text.toString())
                            searchView.clearFocus()
                        } else {
                            binding.rvSearchedShows.visibility = View.VISIBLE
                            searchView.clearFocus()
                        }
                    }
                }
            },
            DELAY
        )
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onQueryTextSubmit(pokemon: String?): Boolean {
        runOnUI {
            pokemon?.let {
                viewModel.search(pokemon)
                searchView.clearFocus()
            }
        }
        return true
    }

    private fun configureSearchView() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        searchView.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(this)

        searchViewText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchViewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

        searchViewText.setHintTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        searchViewText.addTextChangedListener(this)
    }

    fun setSpelledQuery(message: String?) = runOnUI {
        query = message
        val editableText = Editable.Factory().newEditable(message)
        afterTextChanged(editableText)

        searchViewText.text = editableText
        searchViewText.clearFocus()

    }

    companion object {
        private const val DELAY: Long = 1000
        private const val AUTO_SEARCH_TEXT_MIN_SIZE = 3
    }
}