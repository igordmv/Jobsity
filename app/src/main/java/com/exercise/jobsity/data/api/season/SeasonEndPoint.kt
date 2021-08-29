package com.exercise.jobsity.data.api.season

import com.exercise.jobsity.data.api.response.SeasonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SeasonEndPoint {
    @GET("shows/{showId}/seasons")
    suspend fun getSeasons(@Path(value = "showId", encoded = true) showId: Int): List<SeasonResponse>
}