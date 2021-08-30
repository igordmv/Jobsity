package com.exercise.jobsity.domain.usecase

import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class SetFavoriteShowUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun execute(show: Show) = favoriteRepository.setFavorite(show)

}