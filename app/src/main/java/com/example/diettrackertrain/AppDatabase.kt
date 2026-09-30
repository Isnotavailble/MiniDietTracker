package com.example.diettrackertrain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    companion object{
        @Volatile
        var appDatabase : AppDatabase? = null

        fun getDataBase(context: Context) : AppDatabase {
            return appDatabase ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "food_database"
                ).fallbackToDestructiveMigration(true)
                    .build()
                appDatabase = db
                db
            }
        }
    }
}