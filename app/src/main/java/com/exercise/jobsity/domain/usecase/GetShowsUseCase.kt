package com.exercise.jobsity.domain.usecase

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.show.ShowRepository
import javax.inject.Inject

/**
 * Use case for getting shows based at given page.
 */
class GetShowsUseCase @Inject constructor(private val showRepository: ShowRepository) {

    suspend fun execute(page: Int): Resource<List<Show>> = showRepository.getShows(page)

}