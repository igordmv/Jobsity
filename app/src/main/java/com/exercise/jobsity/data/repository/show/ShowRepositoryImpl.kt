package com.exercise.jobsity.data.repository.show

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.response.ShowResponse
import com.exercise.jobsity.data.api.show.ShowApi
import com.exercise.jobsity.data.repository.Mapper
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.show.ShowRepository
import javax.inject.Inject

/**
 * Implementation of [com.exercise.jobsity.domain.repository.show.ShowRepository]
 */
class ShowRepositoryImpl @Inject constructor(
    private val showApi: ShowApi,
    private val mapper: Mapper,
    private val responseHandler: ResponseHandler
) : ShowRepository {

    override suspend fun getShows(page: Int): Resource<List<Show>> {
        val showListResponse : List<ShowResponse>
        return try {
            showListResponse = showApi.getShows(page)
            responseHandler.handleSuccess(mapper.mapShow(showListResponse))
        } catch (e : Exception) {
            responseHandler.handleException(e)
        }
    }
}