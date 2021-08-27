package com.exercise.jobsity.data.api.response

data class NetworkResponse(
    val id: Int,
    val name: String,
    val country: CountryResponse
)