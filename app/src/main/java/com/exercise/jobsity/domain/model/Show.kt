package com.exercise.jobsity.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Show (
    val id : Int,
    val image : String,
    val name : String,
    val summary : String,
    val genres : List<String>,
    val schedule : Schedule
) : Serializable, Parcelable