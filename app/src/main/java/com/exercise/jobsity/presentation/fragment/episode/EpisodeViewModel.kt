package com.exercise.jobsity.presentation.fragment.episode

import android.text.Html
import android.text.Spanned
import androidx.lifecycle.ViewModel
import com.exercise.jobsity.domain.model.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor() : ViewModel() {

    var episode : Episode? = null

    fun updateEpisode(episode: Episode) {
        this.episode = episode
    }

    fun getSummary(): Spanned? {
        episode?.summary?.let {
            return Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        } ?: run { return null }
    }

}