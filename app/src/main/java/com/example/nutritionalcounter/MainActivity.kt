package com.example.nutritionalcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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
                    startDestination = "home" // A kezdőképernyő az új startDestination
                ) {
                    // 0. Kezdőképernyő a 3 gombbal
                    composable("home") {
                        HomeScreen(
                            onNutritionListClick = {
                                navController.navigate("nutrition_list")
                            },
                            onPortionListClick = {
                                // TODO: Navigáció az Adagokhoz
                            },
                            onDailyTotalClick = {
                                // TODO: Navigáció a Napi teljes fogyasztáshoz
                            }
                        )
                    }

                    // Tápérték Lista képernyő
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

                    // Új tápérték hozzáadása képernyő
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
            }
        }
    }
}

// Új Kezdőképernyő Composable
@Composable
fun HomeScreen(
    onNutritionListClick: () -> Unit,
    onPortionListClick: () -> Unit,
    onDailyTotalClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onNutritionListClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Tápanyagok")
        }

        Button(
            onClick = onPortionListClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Elfogyasztott adagok")
        }

        Button(
            onClick = onDailyTotalClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Napi teljes fogyasztás")
        }
    }
}