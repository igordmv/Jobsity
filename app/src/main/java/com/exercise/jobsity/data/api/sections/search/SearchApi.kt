package com.exercise.jobsity.data.api.sections.search

import com.exercise.jobsity.data.api.response.SearchResponse
import javax.inject.Inject

class SearchApi @Inject constructor(private val searchEndPoint: SearchEndPoint) {

    suspend fun search(query: String): List<SearchResponse> = searchEndPoint.search(query)

}