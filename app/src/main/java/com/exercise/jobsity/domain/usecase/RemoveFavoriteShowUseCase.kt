package com.exercise.jobsity.domain.usecase

import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteShowUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun execute(show: Show) = favoriteRepository.removeFavorite(show)

}