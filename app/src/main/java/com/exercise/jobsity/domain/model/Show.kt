package com.exercise.jobsity.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Show (
    val id : Int,
    val image : String?,
    val name : String?,
    val summary : String?,
    val genres : List<String>,
    val schedule : Schedule?,
    val network : Network?,
    val status : String?,
    val type : String?,
    val officialSite : String?,
    val averageRuntime : Int?
) :Serializable, Parcelable