package com.exercise.jobsity.domain.usecase

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Episode
import com.exercise.jobsity.domain.repository.episode.EpisodeRepository
import javax.inject.Inject

class GetSeasonEpisodesUseCase @Inject constructor(private val episodeRepository: EpisodeRepository) {

    suspend fun execute(seasonId: Int): Resource<List<Episode>> =
        episodeRepository.getSeasonEpisodes(seasonId)

}