package com.exercise.jobsity.domain.repository.show

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Show

interface ShowRepository {
    suspend fun getShows(page: Int): Resource<List<Show>>
}