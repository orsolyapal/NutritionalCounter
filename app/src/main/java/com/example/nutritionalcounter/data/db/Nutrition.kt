package com.example.nutritionalcounter.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutritions")
data class Nutrition(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val fet: Double,
    val protein: Double,
    val carbs: Double,
    val fibre: Double,
    val netCarbs: Double,
    val unit: String
)
