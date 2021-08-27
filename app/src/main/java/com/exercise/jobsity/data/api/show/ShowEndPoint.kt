package com.exercise.jobsity.data.api.show

import com.exercise.jobsity.data.api.response.ShowResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowEndPoint {
    @GET("shows")
    suspend fun getShows(@Query("page") page: Int): List<ShowResponse>
}