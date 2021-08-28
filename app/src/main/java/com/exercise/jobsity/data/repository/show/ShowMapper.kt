package com.exercise.jobsity.data.repository.show

import com.exercise.jobsity.data.api.response.NetworkResponse
import com.exercise.jobsity.data.api.response.ScheduleResponse
import com.exercise.jobsity.data.api.response.ShowResponse
import com.exercise.jobsity.domain.model.Network
import com.exercise.jobsity.domain.model.Schedule
import com.exercise.jobsity.domain.model.Show
import javax.inject.Inject

class ShowMapper @Inject constructor() {
    fun map(response: List<ShowResponse>): List<Show> {
        return response.map { it ->
            Show(
                it.id,
                it.image.medium,
                it.name,
                it.summary,
                it.genres,
                it.schedule?.let { mapSchedule(it) },
                it.network?.let { mapNetwork(it) },
                it.status,
                it.type,
                it.officialSite,
                it.averageRuntime
            )
        }
    }

    private fun mapNetwork(data: NetworkResponse): Network =
        Network(data.name, data.country.name, data.country.code)

    private fun mapSchedule(response: ScheduleResponse): Schedule =
        Schedule(response.time, response.days)

}