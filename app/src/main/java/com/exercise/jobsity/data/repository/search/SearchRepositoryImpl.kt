package com.exercise.jobsity.data.repository.search

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.response.SearchResponse
import com.exercise.jobsity.data.api.sections.search.SearchApi
import com.exercise.jobsity.data.repository.Mapper
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.search.SearchRepository
import javax.inject.Inject

/**
 * Implementation of [com.exercise.jobsity.domain.repository.season.SeasonRepository]
 */
class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val mapper: Mapper,
    private val responseHandler: ResponseHandler
) : SearchRepository {
    override suspend fun search(query: String): Resource<List<Show>> {
        val searchResponse: List<SearchResponse>
        return try {
            searchResponse = searchApi.search(query)
            responseHandler.handleSuccess(mapper.mapSearch(searchResponse))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}