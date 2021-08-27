package com.exercise.jobsity.di

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
}