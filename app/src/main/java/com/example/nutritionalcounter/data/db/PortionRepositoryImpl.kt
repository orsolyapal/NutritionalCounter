package com.example.nutritionalcounter.data.db

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class PortionRepositoryImpl(private val portionDao: PortionDao): PortionRepositoryInterface {
    override suspend fun insertPortion(portion: Portion) {
        portionDao.insertPortion(portion)
    }

    override suspend fun updatePortion(portion: Portion) {
        portionDao.updatePortion(portion)
    }

    override suspend fun deletePortion(portion: Portion) {
        portionDao.deletePortion(portion)
    }

    override fun getPortionsWithNutrition(): Flow<List<PortionWithNutrition>> {
        return portionDao.getPortionsWithNutrition()
    }

    override fun getPortionsWithNutritionByDate(date: LocalDate): Flow<List<PortionWithNutrition>> {
        return portionDao.getPortionsWithNutritionByDate(date)
    }
}