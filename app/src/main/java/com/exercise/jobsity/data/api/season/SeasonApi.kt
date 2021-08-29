package com.exercise.jobsity.data.api.season

import com.exercise.jobsity.data.api.response.SeasonResponse
import javax.inject.Inject

class SeasonApi @Inject constructor(private val seasonEndPoint: SeasonEndPoint) {

    suspend fun getSeasons(showId: Int): List<SeasonResponse> = seasonEndPoint.getSeasons(showId)

}