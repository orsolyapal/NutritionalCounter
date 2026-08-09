package com.example.nutritionalcounter.ui.nutrition_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutritionalcounter.data.db.NutritionRepositoryInterface

class NutritionViewModelFactory(
    private val repo: NutritionRepositoryInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionViewModel(repo) as T
        }
        throw IllegalArgumentException("Ismeretlen ViewModel osztály")
    }
}