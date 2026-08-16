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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nutritionalcounter.data.db.PortionRepositoryImpl
import com.example.nutritionalcounter.ui.portion_list.PortionListScreen
import com.example.nutritionalcounter.ui.portion_list.PortionViewModel
import com.example.nutritionalcounter.ui.portion_list.PortionViewModelFactory

import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val nutritionRepository = NutritionRepositoryImpl(database.nutritionDao())
        val portionRepository = PortionRepositoryImpl(database.portionDao())

        setContent {
            NutritionalCounterTheme {
                val navController = rememberNavController()
                val nutritionViewModel: NutritionViewModel = viewModel(
                    factory = NutritionViewModelFactory(nutritionRepository)
                )

                val portionViewModel: PortionViewModel = viewModel(
                    factory = PortionViewModelFactory(portionRepository)
                )

                NavHost(
                    navController = navController,
                    startDestination = "home" // A kezdőképernyő az új startDestination
                ) {
                    // Kezdőképernyő a 3 gombbal
                    composable("home") {
                        HomeScreen(
                            onNutritionListClick = {
                                navController.navigate("nutrition_list")
                            },
                            onPortionListClick = {
                                navController.navigate("portion_list")
                            },
                            onDailyTotalClick = {
                                // TODO: Navigáció a Napi teljes fogyasztáshoz
                            }
                        )
                    }

                    // Tápérték Lista képernyő
                    composable("nutrition_list") {
                        NutritionListScreen(
                            viewModel = nutritionViewModel,
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
                                nutritionViewModel.addNutrition(nutritionItem)
                                navController.popBackStack()
                            },
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // Adag Lista képernyő
                    composable("portion_list") {
                        val portions by portionViewModel.portions.collectAsState(initial = emptyList())
                        val searchQuery by portionViewModel.searchQuery.collectAsState()

                        PortionListScreen(
                            portionList = portions,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { query ->
                                portionViewModel.onSearchQueryChanged(query)
                            },
                            onAddPortionClick = {
                                navController.navigate("add_portion")
                            },
                            onPortionClick = { portionWithNutrition ->
                                navController.navigate("edit_portion/${portionWithNutrition.portion.id}")
                            },
                            onDeletePortionClick = { portionWithNutrition ->
                                portionViewModel.deletePortion(portionWithNutrition.portion)
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