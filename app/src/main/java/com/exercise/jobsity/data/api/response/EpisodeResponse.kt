package com.exercise.jobsity.data.api.response

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    val id: Int?,
    val url: String?,
    val name: String?,
    val season: Int?,
    val number: Int?,
    val type: String?,
    @SerializedName("airdate") val airDate: String?,
    @SerializedName("airtime") val airTime: String?,
    @SerializedName("airstamp") val airStamp: String?,
    val runtime: Int?,
    val image: ImageResponse?,
    val summary: String?,
    @SerializedName("_links") val links : LinksResponse
)