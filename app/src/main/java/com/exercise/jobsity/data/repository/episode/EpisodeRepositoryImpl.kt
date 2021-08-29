package com.exercise.jobsity.data.repository.episode

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.sections.episode.EpisodeApi
import com.exercise.jobsity.data.api.response.EpisodeResponse
import com.exercise.jobsity.data.repository.Mapper
import com.exercise.jobsity.domain.model.Episode
import com.exercise.jobsity.domain.repository.episode.EpisodeRepository
import javax.inject.Inject

/**
 * Implementation of [com.exercise.jobsity.domain.repository.episode.EpisodeRepository]
 */
class EpisodeRepositoryImpl @Inject constructor(
    private val episodeApi: EpisodeApi,
    private val mapper: Mapper,
    private val responseHandler: ResponseHandler
) : EpisodeRepository {
    override suspend fun getSeasonEpisodes(seasonId: Int): Resource<List<Episode>> {
        val episodeListResponse: List<EpisodeResponse>
        return try {
            episodeListResponse = episodeApi.getSeasonEpisodes(seasonId)
            responseHandler.handleSuccess(mapper.mapEpisode(episodeListResponse))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}