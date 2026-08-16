package com.example.nutritionalcounter.ui.portion_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritionalcounter.data.db.DateConverters
import com.example.nutritionalcounter.data.db.Portion
import com.example.nutritionalcounter.data.db.PortionRepositoryInterface
import com.example.nutritionalcounter.data.db.PortionWithNutrition
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class PortionViewModel(private val repository: PortionRepositoryInterface): ViewModel() {

    val selectedDate = MutableStateFlow<LocalDate?>(null)
    val searchQuery = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    val portions: StateFlow<List<PortionWithNutrition>> = selectedDate
        .flatMapLatest { date ->
            if (date == null) {
                repository.getPortionsWithNutrition()
            } else {
                repository.getPortionsWithNutritionByDate(date)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onDateSelected(date: LocalDate?) {
        selectedDate.value = date
    }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun updatePortion(portion: Portion) {
        viewModelScope.launch {
            repository.updatePortion(portion)
        }
    }

    fun addPortion(portion: Portion) {
        viewModelScope.launch {
            repository.insertPortion(portion)
        }
    }

    fun deletePortion(portion: Portion) {
        viewModelScope.launch {
            repository.deletePortion(portion)
        }
    }
}