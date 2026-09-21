package com.example.diettrackertrain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [FoodEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDao

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