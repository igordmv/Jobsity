package com.exercise.jobsity.data.api.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class ShowResponse(
    val id: Int,
    val url: String,
    val name: String,
    val type: String?,
    val language: String,
    val genres: List<String>,
    val status: String?,
    val runtime: Int,
    val averageRuntime: Int,
    val premiered: Date,
    val officialSite : String?,
    val schedule : ScheduleResponse?,
    val rating : RatingResponse,
    val weight : Int,
    val network : NetworkResponse?,
    val externals : ExternalsResponse,
    val image : ImageResponse,
    val summary : String,
    val updated : Int,
    @SerializedName("_links") val links : LinksResponse

)