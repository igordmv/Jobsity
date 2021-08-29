package com.exercise.jobsity.data.api.sections.show

import com.exercise.jobsity.data.api.response.ShowResponse
import javax.inject.Inject

class ShowApi @Inject constructor(private val showEndPoint: ShowEndPoint) {

    suspend fun getShows(page: Int): List<ShowResponse> = showEndPoint.getShows(page)

}