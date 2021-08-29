package com.exercise.jobsity.data.repository

import com.exercise.jobsity.data.api.response.*
import com.exercise.jobsity.domain.model.*
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun mapShow(response: List<ShowResponse>): List<Show> {
        return response.map {
            Show(
                it.id,
                it.image?.medium ?: it.image?.original ?: "",
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

    fun mapSeason(response: List<SeasonResponse>): List<Season> {
        return response.map {
            Season(
                it.id,
                it.number,
                it.episodeOrder,
                it.url,
                it.image?.medium,
                it.summary
            )
        }
    }

    fun mapEpisode(response: List<EpisodeResponse>): List<Episode> {
        return response.map {
            Episode(
                it.id,
                it.name,
                it.summary,
                it.image?.medium,
                it.number,
                it.season,
                it.type,
                it.airDate,
                it.airTime,
                it.runtime
            )
        }
    }

    private fun mapNetwork(data: NetworkResponse): Network =
        Network(data.name, data.country.name, data.country.code)

    private fun mapSchedule(response: ScheduleResponse): Schedule =
        Schedule(response.time, response.days)

}