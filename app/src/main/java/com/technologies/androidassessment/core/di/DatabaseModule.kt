package com.technologies.androidassessment.core.di

import android.content.Context
import androidx.room.Room
import com.technologies.androidassessment.core.data.dao.AirportDao
import com.technologies.androidassessment.core.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDataDao(appDataBase: AppDatabase): AirportDao {
        return appDataBase.airportDao()
    }

}