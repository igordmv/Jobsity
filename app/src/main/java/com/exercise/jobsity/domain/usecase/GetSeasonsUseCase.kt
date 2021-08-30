package com.exercise.jobsity.domain.usecase

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Season
import com.exercise.jobsity.domain.repository.season.SeasonRepository
import javax.inject.Inject

/**
 * Use case for getting seasons from a show.
 */
class GetSeasonsUseCase @Inject constructor(private val seasonRepository: SeasonRepository) {

    suspend fun execute(showId: Int): Resource<List<Season>> = seasonRepository.getSeasons(showId)

}