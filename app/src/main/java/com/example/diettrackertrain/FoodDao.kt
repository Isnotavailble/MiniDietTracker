package com.example.diettrackertrain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(food: FoodEntity)

    @Query("SELECT * FROM food_tbl")
    suspend fun findAll(): List<FoodEntity>

    @Query("SELECT * FROM food_tbl WHERE category LIKE '%' || :category || '%'")
    suspend fun findByCategory(category: String): List<FoodEntity>
}