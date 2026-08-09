package com.example.nutritionalcounter.data.db

import kotlinx.coroutines.flow.Flow

interface NutritionRepositoryInterface {
    fun getAllNutritions(): Flow<List<Nutrition>>
    fun searchNutritionsByNameExcerpt(nameExcerpt: String): Flow<List<Nutrition>>
    suspend fun getNutritionById(id: Int): Nutrition?
    suspend fun insertNutrition(nutrition: Nutrition)
    suspend fun updateNutrition(nutrition: Nutrition)
    suspend fun deleteNutrition(nutrition: Nutrition)
}