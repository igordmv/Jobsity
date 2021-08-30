package com.exercise.jobsity.di

import com.exercise.jobsity.data.api.ResponseHandler
import com.exercise.jobsity.data.api.sections.episode.EpisodeApi
import com.exercise.jobsity.data.api.sections.favorite.FavoriteApi
import com.exercise.jobsity.data.api.sections.search.SearchApi
import com.exercise.jobsity.data.api.sections.season.SeasonApi
import com.exercise.jobsity.data.api.sections.show.ShowApi
import com.exercise.jobsity.data.repository.Mapper
import com.exercise.jobsity.data.repository.episode.EpisodeRepositoryImpl
import com.exercise.jobsity.data.repository.favorite.FavoriteRepositoryImpl
import com.exercise.jobsity.data.repository.search.SearchRepositoryImpl
import com.exercise.jobsity.data.repository.season.SeasonRepositoryImpl
import com.exercise.jobsity.data.repository.show.ShowRepositoryImpl
import com.exercise.jobsity.domain.repository.episode.EpisodeRepository
import com.exercise.jobsity.domain.repository.favorite.FavoriteRepository
import com.exercise.jobsity.domain.repository.search.SearchRepository
import com.exercise.jobsity.domain.repository.season.SeasonRepository
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
    fun provideShowRepository(
        showApi: ShowApi,
        mapper: Mapper,
        responseHandler: ResponseHandler
    ): ShowRepository {
        return ShowRepositoryImpl(showApi, mapper, responseHandler)
    }

    @Provides
    @Singleton
    fun provideSeasonRepository(
        seasonApi: SeasonApi,
        mapper: Mapper,
        responseHandler: ResponseHandler
    ): SeasonRepository {
        return SeasonRepositoryImpl(seasonApi, mapper, responseHandler)
    }

    @Provides
    @Singleton
    fun provideEpisodeRepository(
        episodeApi: EpisodeApi,
        mapper: Mapper,
        responseHandler: ResponseHandler
    ): EpisodeRepository {
        return EpisodeRepositoryImpl(episodeApi, mapper, responseHandler)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchApi: SearchApi,
        mapper: Mapper,
        responseHandler: ResponseHandler
    ): SearchRepository {
        return SearchRepositoryImpl(searchApi, mapper, responseHandler)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteApi: FavoriteApi,
        responseHandler: ResponseHandler
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteApi, responseHandler)
    }
}