package com.example.nutritionalcounter.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PortionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortion(portion: Portion)

    @Update
    suspend fun updatePortion(portion: Portion)

    @Delete
    suspend fun deletePortion(portion: Portion)

    @Transaction
    @Query("select * from portions")
    fun getPortionsWithNutrition(): Flow<List<PortionWithNutrition>>

    @Transaction
    @Query("select * from portions where portions.date = :date")
    fun getPortionsWithNutritionByDate(date: LocalDate): Flow<List<PortionWithNutrition>>
}