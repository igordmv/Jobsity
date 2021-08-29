package com.exercise.jobsity.data.api.episode

import com.exercise.jobsity.data.api.response.EpisodeResponse
import javax.inject.Inject

class EpisodeApi @Inject constructor(private val episodeEndPoint: EpisodeEndPoint) {

    suspend fun getSeasonEpisodes(seasonId: Int): List<EpisodeResponse> = episodeEndPoint.getSeasonEpisodes(seasonId)

}