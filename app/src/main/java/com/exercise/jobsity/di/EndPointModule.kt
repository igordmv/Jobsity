package com.exercise.jobsity.di

import com.exercise.jobsity.data.api.episode.EpisodeEndPoint
import com.exercise.jobsity.data.api.season.SeasonEndPoint
import com.exercise.jobsity.data.api.show.ShowEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class EndPointModule {

    @Provides
    @Singleton
    fun provideShowEndPoint(retrofit: Retrofit): ShowEndPoint {
        return retrofit.create(ShowEndPoint::class.java)
    }

    @Provides
    @Singleton
    fun provideSeasonEndPoint(retrofit: Retrofit): SeasonEndPoint {
        return retrofit.create(SeasonEndPoint::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodeEndPoint(retrofit: Retrofit): EpisodeEndPoint {
        return retrofit.create(EpisodeEndPoint::class.java)
    }
}