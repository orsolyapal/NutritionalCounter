package com.example.nutritionalcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nutritionalcounter.ui.nutrition_list.NutritionListScreen
import com.example.nutritionalcounter.ui.nutrition_list.NutritionViewModel
import com.example.nutritionalcounter.ui.theme.NutritionalCounterTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritionalcounter.data.db.AppDatabase
import com.example.nutritionalcounter.data.db.NutritionRepositoryImpl
import com.example.nutritionalcounter.ui.nutrition_list.NutritionViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Példa: Adatbázis és Repository kézi példányosítása (a saját architektúrád szerint)
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = NutritionRepositoryImpl(database.nutritionDao())

        setContent {
            NutritionalCounterTheme {
                val viewModel: NutritionViewModel = viewModel(
                    factory = NutritionViewModelFactory(repository)
                )

                NutritionListScreen(
                    viewModel = viewModel,
                    onAddNutritionClick = {
                        // Navigáció az új tápérték hozzáadása képernyőre
                    },
                    onNutritionClick = {
                        // selectedNutrition ->
                        // Navigáció a részletek / szerkesztés képernyőre
                    }
                )
            }
        }
    }
}