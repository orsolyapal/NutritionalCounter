package com.example.nutritionalcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nutritionalcounter.ui.nutrition_list.NutritionListScreen
import com.example.nutritionalcounter.ui.nutrition_list.NutritionViewModel
import com.example.nutritionalcounter.ui.theme.NutritionalCounterTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutritionalcounter.data.db.AppDatabase
import com.example.nutritionalcounter.data.db.NutritionRepositoryImpl
import com.example.nutritionalcounter.ui.nutrition_list.NutritionViewModelFactory
import com.example.nutritionalcounter.ui.nutrition_add.AddNutritionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = NutritionRepositoryImpl(database.nutritionDao())

        setContent {
            NutritionalCounterTheme {
                val navController = rememberNavController()
                val viewModel: NutritionViewModel = viewModel(
                    factory = NutritionViewModelFactory(repository)
                )

                NavHost(
                    navController = navController,
                    startDestination = "nutrition_list"
                ) {
                    // 1. Lista képernyő
                    composable("nutrition_list") {
                        NutritionListScreen(
                            viewModel = viewModel,
                            onAddNutritionClick = {
                                navController.navigate("add_nutrition")
                            },
                            onNutritionClick = { nutrition ->
                                navController.navigate("edit_nutrition/${nutrition.id}")
                            }
                        )
                    }

                    // 2. Új tápérték hozzáadása képernyő
                    composable("add_nutrition") {
                        AddNutritionScreen(
                            onSaveClick = { nutritionItem ->
                                viewModel.addNutrition(nutritionItem)
                                navController.popBackStack()
                            },
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
/*
                NutritionListScreen(
                    viewModel = viewModel,
                    onAddNutritionClick = {
                        // Navigáció az új tápérték hozzáadása képernyőre
                    },
                    onNutritionClick = {
                        // selectedNutrition ->
                        // Navigáció a részletek / szerkesztés képernyőre
                    }
                )*/
            }
        }
    }
}