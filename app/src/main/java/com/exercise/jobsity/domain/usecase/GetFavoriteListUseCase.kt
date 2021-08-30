package com.exercise.jobsity.domain.usecase

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun execute(): Resource<List<Show>> = favoriteRepository.getFavoriteList()

}