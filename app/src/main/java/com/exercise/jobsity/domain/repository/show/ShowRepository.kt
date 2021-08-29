package com.exercise.jobsity.domain.repository.show

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Season
import com.exercise.jobsity.domain.model.Show

interface ShowRepository {

    /**
     * To get shows based on page ID
     *
     *  @param page pageId which will be returned
     *  @return Resource which holds the list of shows if success or error if fails
     */
    suspend fun getShows(page: Int): Resource<List<Show>>
}