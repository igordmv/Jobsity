package com.exercise.jobsity.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Schedule(
    val time : String,
    val days : List<String>
) : Serializable, Parcelable
