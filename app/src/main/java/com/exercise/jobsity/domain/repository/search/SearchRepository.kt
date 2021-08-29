package com.exercise.jobsity.domain.repository.search

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Show

interface SearchRepository {

    /**
     * To get shows based on query searched
     *
     *  @param query query typed by user to be searched
     *  @return Resource which holds the list of shows that match the query if success or error if fails
     */
    suspend fun search(query: String): Resource<List<Show>>
}