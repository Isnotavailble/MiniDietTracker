package com.example.diettrackertrain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods_table")
    suspend fun findAllFood() : List<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(food : FoodEntity)

    @Query("SELECT * FROM foods_table WHERE category = :category ")
    suspend fun findByCategory(category : String) : List<FoodEntity>


}