package com.example.nutritionalcounter.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class PortionWithNutrition(
    @Embedded val portion: Portion,
    @Relation(
        parentColumn = "nutritionId",
        entityColumn = "id"
    )
    val nutrition: Nutrition
)
