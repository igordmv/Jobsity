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
import com.exercise.jobsity.domain.model.Schedule
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

        if(selectedShow.genres.isNotEmpty()) {
            val formattedGenres = formatGenres(selectedShow.genres)
            binding.tvGenresContent.visibility = View.VISIBLE
            binding.tvGenresHeader.visibility = View.VISIBLE
            binding.tvGenresContent.text = formattedGenres
        }

        selectedShow.network?.let { network ->
            binding.tvNetworkContent.visibility = View.VISIBLE
            binding.tvNetworkHeader.visibility = View.VISIBLE
            binding.tvNetworkContent.text = "${network.name} (${network.countryCode})"
        }

        selectedShow.schedule?.let { schedule ->
            var formattedSchedule = formatSchedule(schedule)
            selectedShow.averageRuntime?.let {
                formattedSchedule += " ($it min)"
            }
            binding.tvScheduleContent.visibility = View.VISIBLE
            binding.tvScheduleHeader.visibility = View.VISIBLE
            binding.tvScheduleContent.text = formattedSchedule
        }

        selectedShow.status?.let { status ->
            binding.tvStatusContent.visibility = View.VISIBLE
            binding.tvStatusHeader.visibility = View.VISIBLE
            binding.tvStatusContent.text = status
        }

        selectedShow.type?.let { type ->
            binding.tvShowTypeContent.visibility = View.VISIBLE
            binding.tvShowTypeHeader.visibility = View.VISIBLE
            binding.tvShowTypeContent.text = type
        }

        selectedShow.officialSite?.let { site ->
            binding.tvOfficialSiteContent.visibility = View.VISIBLE
            binding.tvOfficialSiteHeader.visibility = View.VISIBLE
            binding.tvOfficialSiteContent.text = site
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun formatSchedule(schedule: Schedule): String {
        if(schedule.days.size == 1) {
            return "${schedule.days.first()}'s at ${schedule.time}"
        }
        var formattedSchedule = ""
        schedule.days.forEach { day ->
            formattedSchedule += "${day}'s, "
        }
        formattedSchedule += "at ${schedule.time}"
        return formattedSchedule
    }

    private fun formatGenres(genres: List<String>): String {
        if (genres.size == 1)
            return "| ${genres.first()} |"
        var formattedGenres = "| "
        genres.forEach {
            formattedGenres += "$it | "
        }
        return formattedGenres
    }
}