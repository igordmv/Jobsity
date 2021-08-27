package com.exercise.jobsity.di

import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.show.ShowApi
import com.exercise.jobsity.data.repository.show.ShowMapper
import com.exercise.jobsity.data.repository.show.ShowRepositoryImpl
import com.exercise.jobsity.domain.repository.show.ShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection for repository classes.
 */

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideShowRepository(showApi: ShowApi, showMapper: ShowMapper, responseHandler: ResponseHandler): ShowRepository {
        return ShowRepositoryImpl(showApi, showMapper, responseHandler)
    }
}