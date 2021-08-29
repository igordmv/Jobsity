package com.exercise.jobsity.data.api.episode

import com.exercise.jobsity.data.api.response.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeEndPoint {
    @GET("seasons/{seasonId}/episodes")
    suspend fun getSeasonEpisodes(
        @Path(
            value = "seasonId",
            encoded = true
        ) showId: Int
    ): List<EpisodeResponse>
}