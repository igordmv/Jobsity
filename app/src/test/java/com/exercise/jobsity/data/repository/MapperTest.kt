package com.exercise.jobsity.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exercise.jobsity.data.api.response.*
import com.exercise.jobsity.domain.model.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class MapperTest {

    //Model data
    private val imageResponse = ImageResponse("medium", "original")
    private val countryResponse = CountryResponse("name", "code", "timezone")
    private val networkResponse = NetworkResponse(0, "name", countryResponse)
    private val scheduleResponse = ScheduleResponse("time", listOf("day1", "day2"))
    private val showResponse = ShowResponse(
        1, "url", "name", "type", "language",
        listOf("genre1", "genre2"), "status", 60, 60, Date(),
        "officialSite", scheduleResponse, null, 0, networkResponse,
        null, imageResponse, "summary", 0, null
    )
    private val seasonResponse = SeasonResponse(
        0, "url", 1, "name", 1, "premiereDate",
        "endDate", networkResponse, imageResponse, "summary", null
    )
    private val episodeResponse = EpisodeResponse(0, "url", "name", 1, 1, "type", "airDate",
    "airTime", "airStamp", 60, imageResponse, "summary", null)
    private val searchResponse = SearchResponse(0.0, showResponse)

    //Model domain
    private val schedule = Schedule(scheduleResponse.time, scheduleResponse.days)
    private val network =
        Network(networkResponse.name, networkResponse.country.name, networkResponse.country.code)
    private val show =
        Show(
            showResponse.id,
            showResponse.image?.medium,
            showResponse.name,
            showResponse.summary,
            showResponse.genres,
            schedule,
            network,
            showResponse.status,
            showResponse.type,
            showResponse.officialSite,
            showResponse.averageRuntime
        )
    private val season = Season(0, 1, 1, "url", "medium", "summary")
    private val episode = Episode(0, "name", "summary", "medium", 1, 1, "type", "airDate",
    "airTime", 60)

    private lateinit var mapper: Mapper

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mapper = Mapper()
    }

    @Test
    fun `test mapShow`() {
        val mappedShow = mapper.mapShow(listOf(showResponse))
        assert(mappedShow == listOf(show))
    }

    @Test
    fun `test mapSeason`() {
        val mappedSeason = mapper.mapSeason(listOf(seasonResponse))
        assert(mappedSeason == listOf(season))
    }

    @Test
    fun `test mapEpisode`() {
        val mappedEpisode = mapper.mapEpisode(listOf(episodeResponse))
        assert(mappedEpisode == listOf(episode))
    }

    @Test
    fun `test mapSearch`() {
        val mappedSearch = mapper.mapSearch(listOf(searchResponse))
        assert(mappedSearch == listOf(show))
    }
}