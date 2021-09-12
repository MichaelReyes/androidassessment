package com.technologies.androidassessment.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technologies.androidassessment.core.data.dao.AirportDao
import com.technologies.androidassessment.core.data.entity.Airport

@Database(
    entities = [Airport::class],
    version = AppDatabase.VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "androidassessment.db"
        const val VERSION = 1
    }

    abstract fun airportDao(): AirportDao

}