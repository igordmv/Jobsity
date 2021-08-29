package com.exercise.jobsity.data.api.response

import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    val id : Int,
    val url : String?,
    val number : Int?,
    val name : String?,
    val episodeOrder : Int?,
    val premiereDate : String?,
    val endDate : String?,
    val network : NetworkResponse?,
    val image : ImageResponse?,
    val summary : String?,
    @SerializedName("_links") val links : LinksResponse
)
