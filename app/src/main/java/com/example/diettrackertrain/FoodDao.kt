package com.example.diettrackertrain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    suspend fun findAll() : List<FoodEntity>

    @Query("SELECT * FROM food_table WHERE category LIKE '%' || :category || '%'")
    suspend fun findByCategory(category : String) : List<FoodEntity>

    @Insert
    suspend fun addFood(food : FoodEntity)
}