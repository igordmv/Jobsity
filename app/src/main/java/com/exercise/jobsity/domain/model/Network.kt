package com.exercise.jobsity.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Network(
    val name: String?,
    val countryName: String?,
    val countryCode: String?
) : Serializable, Parcelable