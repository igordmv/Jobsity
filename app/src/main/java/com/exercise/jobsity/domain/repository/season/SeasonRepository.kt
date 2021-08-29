package com.exercise.jobsity.domain.repository.season

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Season

interface SeasonRepository {

    /**
     * To get seasons based on show ID
     *
     *  @param showId the ID of the show
     *  @return Resource which holds the list of seasons if success or error if fails
     */
    suspend fun getSeasons(showId: Int): Resource<List<Season>>
}