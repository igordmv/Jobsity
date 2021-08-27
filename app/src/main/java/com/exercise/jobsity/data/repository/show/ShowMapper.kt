package com.exercise.jobsity.data.repository.show

import com.exercise.jobsity.data.api.response.ScheduleResponse
import com.exercise.jobsity.data.api.response.ShowResponse
import com.exercise.jobsity.domain.model.Schedule
import com.exercise.jobsity.domain.model.Show
import javax.inject.Inject

class ShowMapper @Inject constructor() {
    fun map(response: List<ShowResponse>): List<Show> {
        return response.map {
            Show(
                it.id,
                it.image.medium,
                it.name,
                it.summary,
                it.genres,
                mapSchedule(it.schedule)
            )
        }
    }

    private fun mapSchedule(response: ScheduleResponse): Schedule {
        return Schedule(response.time, response.days)
    }
}