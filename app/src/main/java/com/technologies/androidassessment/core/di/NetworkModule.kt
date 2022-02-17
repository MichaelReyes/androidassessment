package com.technologies.androidassessment.core.di

import com.technologies.androidassessment.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 *
 * Dagger module to handle Retrofit initialization for injection
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    internal val loggingInterceptor: HttpLoggingInterceptor
        @Provides
        get() = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )

    @Provides
    @Singleton
    internal fun getHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder
            .addInterceptor(interceptor)
            .addInterceptor(httpApiInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideApiRetrofitUsers(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun httpApiInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            request = request.newBuilder()
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
    }

}