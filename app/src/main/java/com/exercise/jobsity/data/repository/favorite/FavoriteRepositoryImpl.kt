package com.exercise.jobsity.data.repository.favorite

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.sections.favorite.FavoriteApi
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.favorite.FavoriteRepository
import java.lang.Exception
import javax.inject.Inject

/**
 * Implementation of [com.exercise.jobsity.domain.repository.favorite.FavoriteRepository]
 */
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteApi: FavoriteApi,
    private val responseHandler: ResponseHandler
) : FavoriteRepository {

    override suspend fun setFavorite(show: Show) {
        favoriteApi.setFavorite(show)
    }

    override suspend fun removeFavorite(show: Show) {
        favoriteApi.removeFavorite(show)
    }

    override suspend fun getFavoriteList(): Resource<List<Show>> {
        return try {
            responseHandler.handleSuccess(favoriteApi.getFavoriteList())
        } catch (e : Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getIfShowIsFavorite(show: Show): Resource<Boolean> {
        return try {
            responseHandler.handleSuccess(favoriteApi.getIfShowIsFavorite(show))
        } catch (e : Exception) {
            responseHandler.handleException(e)
        }
    }
}