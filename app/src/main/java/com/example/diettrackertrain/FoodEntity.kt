package com.example.diettrackertrain

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "foods_table")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val calories : Int = 0,
    val fibres : Int = 0,
    val category : String,
)
