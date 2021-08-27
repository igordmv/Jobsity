package com.exercise.jobsity.data.api.response

import com.google.gson.annotations.SerializedName

data class ExternalsResponse(
    @SerializedName("tvrage") val tvRage: Int,
    @SerializedName("thetvdb") val theTvDb: Int,
    val imdb: String,
)