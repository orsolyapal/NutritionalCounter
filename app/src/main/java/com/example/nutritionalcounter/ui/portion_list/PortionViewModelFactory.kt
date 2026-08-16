package com.example.nutritionalcounter.ui.portion_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutritionalcounter.data.db.PortionRepositoryInterface

class PortionViewModelFactory(private val repo: PortionRepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PortionViewModel(repo) as T
        }
        throw IllegalArgumentException("Ismeretlen ViewModel osztály")
    }
}