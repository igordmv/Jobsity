package com.exercise.jobsity.presentation.fragment.show

import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.exercise.jobsity.R
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Episode
import com.exercise.jobsity.domain.model.Schedule
import com.exercise.jobsity.domain.model.Season
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.GetSeasonEpisodesUseCase
import com.exercise.jobsity.domain.usecase.GetSeasonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(
    private val getSeasonsUseCase: GetSeasonsUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase
) : ViewModel() {

    private val loadingObservable = MutableLiveData<Boolean>().apply { value = true }
    private val seasonsObservable = MutableLiveData<List<Season>>().apply { value = emptyList() }
    private val episodesObservable = MutableLiveData<List<Episode>>().apply { value = emptyList() }
    var episodeQuantityObservable = MutableLiveData<String>().apply { value = "" }
    var show: Show? = null

    fun getLoadingLiveData(): LiveData<Boolean> = loadingObservable
    fun getSeasonLiveData(): LiveData<List<Season>> = seasonsObservable
    fun getEpisodeLiveData(): LiveData<List<Episode>> = episodesObservable

    fun setSelectedShow(selectedShow: Show) {
        show = selectedShow
        fetchSeasons(selectedShow.id ?: 0)
    }

    fun getDescription(): Spanned? {
        show?.summary?.let {
            return Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        } ?: run { return null }
    }

    fun getNetworkText(): String {
        return "${show?.network?.name} (${show?.network?.countryCode})"
    }

    fun getScheduleText(): String {
        show?.schedule?.let { schedule ->
            var formattedSchedule = formatSchedule(schedule)
            show?.averageRuntime?.let {
                formattedSchedule += " ($it min)"
            }
            return formattedSchedule
        }
        return ""
    }

    fun getGenresText(): String {
        show?.genres?.let {
            return formatGenres(it)
        }
        return ""
    }

    fun getDescriptionVisibility(): Int {
        return show?.summary?.let { View.VISIBLE } ?: View.GONE
    }

    fun getNetworkVisibility(): Int {
        return show?.network?.let { View.VISIBLE } ?: View.GONE
    }

    fun getScheduleVisibility(): Int {
        return show?.schedule?.let { View.VISIBLE } ?: View.GONE
    }

    fun getStatusVisibility(): Int {
        return show?.status?.let { View.VISIBLE } ?: View.GONE
    }

    fun getTypeVisibility(): Int {
        return show?.type?.let { View.VISIBLE } ?: View.GONE
    }

    fun getGenresVisibility(): Int {
        return if (show?.genres?.isNotEmpty() == true)
            View.VISIBLE
        else
            View.GONE
    }

    fun getSiteVisibility(): Int {
        return show?.officialSite?.let { View.VISIBLE } ?: View.GONE
    }

    fun seasonSelected(season: Season) {
        loadingObservable.postValue(true)
        episodeQuantityObservable.postValue("${season.episodeOrder} Episodes")
        fetchSeasonEpisodes(season.id)
    }

    private fun fetchSeasonEpisodes(seasonId: Int) = CoroutineScope(Dispatchers.IO).launch {
        val episodesRequest = getSeasonEpisodesUseCase.execute(seasonId)
        when (episodesRequest.status) {
            Status.SUCCESS -> {
                handleEpisodeRequestSuccess(episodesRequest.data)
            }
            Status.ERROR -> {
            }
        }
        loadingObservable.postValue(false)
    }

    private fun fetchSeasons(showId: Int) = CoroutineScope(Dispatchers.IO).launch {
        val seasonRequest = getSeasonsUseCase.execute(showId)
        when (seasonRequest.status) {
            Status.SUCCESS -> {
                handleSeasonRequestSuccess(seasonRequest.data)
            }
            Status.ERROR -> {
            }
        }
    }

    private fun handleEpisodeRequestSuccess(data: List<Episode>?) {
        data?.let { episodes ->
            episodesObservable.postValue(episodes)
        }
    }

    private fun handleSeasonRequestSuccess(data: List<Season>?) {
        data?.let { seasons ->
            seasonsObservable.postValue(seasons)
        }
    }

    private fun formatSchedule(schedule: Schedule): String {
        if (schedule.days.size == 1) {
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