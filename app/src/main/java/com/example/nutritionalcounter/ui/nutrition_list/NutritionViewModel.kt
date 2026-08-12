package com.example.nutritionalcounter.ui.nutrition_list

import com.example.nutritionalcounter.data.db.Nutrition
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritionalcounter.data.db.NutritionRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NutritionViewModel(private val repository: NutritionRepositoryInterface): ViewModel() {
    val searchQuery = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    val nutritions: StateFlow<List<Nutrition>> = searchQuery
        .flatMapLatest { nameExcerpt ->
            if (nameExcerpt.isBlank()) {
                repository.getAllNutritions()
            } else {
                repository.searchNutritionsByNameExcerpt(nameExcerpt)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun updateNutrition(nutrition: Nutrition) {
        viewModelScope.launch {
            repository.updateNutrition(nutrition)
        }
    }

    fun addNutrition(nutrition: Nutrition) {
        viewModelScope.launch {
            repository.insertNutrition(nutrition)
        }
    }

    fun deleteNutrition(nutrition: Nutrition) {
        viewModelScope.launch {
            repository.deleteNutrition(nutrition)
        }
    }
}