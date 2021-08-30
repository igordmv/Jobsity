package com.exercise.jobsity.domain.repository.favorite

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.domain.model.Show

interface FavoriteRepository {

    /**
     * To set show to favorite
     *
     *  @param show show that will be saved as favorite
     */
    suspend fun setFavorite(show : Show)

    /**
     * To remove show from favorite
     *
     *  @param show show that will be saved as favorite
     */
    suspend fun removeFavorite(show : Show)

    /**
     * To get show favorite list
     *
     * @return favorite list
     */
    suspend fun getFavoriteList() : Resource<List<Show>>

    /**
     * To get if show is favorite
     *
     * @return if show is favorite
     */
    suspend fun getIfShowIsFavorite(show: Show): Resource<Boolean>
}