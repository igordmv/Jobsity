package com.exercise.jobsity.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.exercise.jobsity.BaseApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule() {

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain: Interceptor.Chain? ->
            val original = chain!!.request()

            // Request customization: add request headers
            val response: okhttp3.Response

            val requestBuilder: Request.Builder = original.newBuilder()
                    .method(original.method, original.body)

            val request = requestBuilder.build()
            response = chain.proceed(request)

            return@addInterceptor response
        }
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl("https://api.tvmaze.com/")
            .client(okHttpClient)
            .build()
    }
}
