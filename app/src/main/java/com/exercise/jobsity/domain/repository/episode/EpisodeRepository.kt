package com.exercise.jobsity.domain.repository.episode

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Episode

interface EpisodeRepository {

    /**
     * To get shows based on page ID
     *
     *  @param seasonId seasonId which will be returned
     *  @return Resource which holds the list of episodes if success or error if fails
     */
    suspend fun getSeasonEpisodes(seasonId: Int): Resource<List<Episode>>
}