package com.example.nutritionalcounter.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PortionDao {

    @Transaction
    @Query("select * from portions")
    fun getPortitionsWithNutrition(): Flow<List<PortionWithNutrition>>
}