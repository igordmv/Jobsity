package com.exercise.jobsity.data.api.sections.search

import com.exercise.jobsity.data.api.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchEndPoint {
    @GET("search/shows")
    suspend fun search(
        @Query("q") query: String
    ): List<SearchResponse>
}