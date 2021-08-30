package com.exercise.jobsity.domain.usecase

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.search.SearchRepository
import javax.inject.Inject

/**
 * Use case for searching shows based on given query.
 */
class SearchShowUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    suspend fun execute(query: String): Resource<List<Show>> = searchRepository.search(query)

}