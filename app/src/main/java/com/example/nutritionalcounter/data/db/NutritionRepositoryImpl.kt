package com.example.nutritionalcounter.data.db

import kotlinx.coroutines.flow.Flow

class NutritionRepositoryImpl(private val nutritionDao: NutritionDao) : NutritionRepositoryInterface {

    override fun getAllNutritions(): Flow<List<Nutrition>> {
        return nutritionDao.getAllNutritions()
    }

    override fun searchNutritionsByNameExcerpt(nameExcerpt: String): Flow<List<Nutrition>> {
        return nutritionDao.searchNutritionsByNameExcerpt(nameExcerpt)
    }

    override suspend fun getNutritionById(id: Int): Nutrition? {
        return nutritionDao.getNutritionById(id)
    }

    override suspend fun insertNutrition(nutrition: Nutrition) {
        return nutritionDao.insertNutrition(nutrition)
    }

    override suspend fun updateNutrition(nutrition: Nutrition) {
        return nutritionDao.updateNutrition(nutrition)
    }

    override suspend fun deleteNutrition(nutrition: Nutrition) {
        return nutritionDao.deleteNutrition(nutrition)
    }
}