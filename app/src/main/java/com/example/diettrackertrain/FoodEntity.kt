package com.example.diettrackertrain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo

@Entity(tableName = "food_tbl")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val calories : Int = 0,
    val fibers : Int = 0,
    val proteins : Int = 0,
    val category : String
)
