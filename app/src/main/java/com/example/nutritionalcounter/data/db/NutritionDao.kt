package com.example.nutritionalcounter.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrition(nutrition: Nutrition)

    @Update
    suspend fun updateNutrition(nutrition: Nutrition)

    @Delete
    suspend fun deleteNutrition(nutrition: Nutrition)

    @Query("SELECT * FROM nutritions WHERE id = :nutritionId")
    suspend fun getNutritionById(nutritionId: Int): Nutrition?

    @Query("SELECT * FROM nutritions ORDER BY name")
    fun getAllNutritions() : Flow<List<Nutrition>>

    @Query("SELECT * FROM nutritions WHERE name LIKE '%' || :nameExcerpt || '%' ORDER BY name")
    fun searchNutritionsByNameExcerpt(nameExcerpt: String) : Flow<List<Nutrition>>
}