package com.exercise.jobsity.data.api.response

import com.google.gson.annotations.SerializedName

data class LinksResponse(
    val self : SelfResponse,
    @SerializedName("previousepisode") val previousEpisode : PreviousEpisodeResponse
)
