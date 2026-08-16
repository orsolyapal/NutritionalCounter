package com.example.nutritionalcounter.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "portions",
    foreignKeys = [
        ForeignKey(
            entity = Nutrition::class,
            parentColumns = ["id"],
            childColumns = ["nutritionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["nutritionId"]),
        Index(value = ["date"])
    ]
)
data class Portion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDate,
    val nutritionId: Int,
    val amount: Double
)
