package com.exercise.jobsity.data.repository.season

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.response.SeasonResponse
import com.exercise.jobsity.data.api.sections.season.SeasonApi
import com.exercise.jobsity.data.repository.Mapper
import com.exercise.jobsity.domain.model.Season
import com.exercise.jobsity.domain.repository.season.SeasonRepository
import javax.inject.Inject

/**
 * Implementation of [com.exercise.jobsity.domain.repository.season.SeasonRepository]
 */
class SeasonRepositoryImpl @Inject constructor(
    private val seasonApi: SeasonApi,
    private val mapper: Mapper,
    private val responseHandler: ResponseHandler
) : SeasonRepository {

    override suspend fun getSeasons(showId: Int): Resource<List<Season>> {
        val seasonListResponse: List<SeasonResponse>
        return try {
            seasonListResponse = seasonApi.getSeasons(showId)
            responseHandler.handleSuccess(mapper.mapSeason(seasonListResponse))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}