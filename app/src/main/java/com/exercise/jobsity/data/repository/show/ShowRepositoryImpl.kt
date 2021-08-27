package com.exercise.jobsity.data.repository.show

import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.response.ShowResponse
import com.exercise.jobsity.data.api.show.ShowApi
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.repository.show.ShowRepository
import java.lang.Exception
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val showApi: ShowApi,
    private val showMapper: ShowMapper,
    private val responseHandler: ResponseHandler
) : ShowRepository {
    override suspend fun getShows(page: Int): Resource<List<Show>> {
        val showListResponse : List<ShowResponse>
        return try {
            showListResponse = showApi.getShows(page)
            responseHandler.handleSuccess(showMapper.map(showListResponse))
        } catch (e : Exception) {
            responseHandler.handleException(e)
        }
    }
}