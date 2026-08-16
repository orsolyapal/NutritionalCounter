package com.example.nutritionalcounter.data.db

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface PortionRepositoryInterface {
    suspend fun insertPortion(portion: Portion)
    suspend fun updatePortion(portion: Portion)
    suspend fun deletePortion(portion: Portion)
    fun getPortionsWithNutrition(): Flow<List<PortionWithNutrition>>
    fun getPortionsWithNutritionByDate(date: LocalDate): Flow<List<PortionWithNutrition>>
}