package com.exercise.jobsity.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection for SharedPreferences.
 */
@InstallIn(SingletonComponent::class)
@Module
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun providesSharedPreference(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
    }

    companion object {
        private const val MY_PREFS_NAME = "com.jobsity.shared.preference"
    }
}