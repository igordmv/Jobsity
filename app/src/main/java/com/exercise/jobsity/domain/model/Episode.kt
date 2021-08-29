package com.exercise.jobsity.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Episode(
    val id : Int?,
    val name: String?,
    val summary: String?,
    val image: String?,
    val number: Int?,
    val season: Int?,
    val type: String?,
    val airDate: String?,
    val airTime: String?,
    val runtime : Int?
) : Serializable, Parcelable
