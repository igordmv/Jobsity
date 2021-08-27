package com.exercise.jobsity.domain.model

data class Show (
    val id : Int,
    val image : String,
    val name : String,
    val summary : String,
    val genres : List<String>,
    val schedule : Schedule
)