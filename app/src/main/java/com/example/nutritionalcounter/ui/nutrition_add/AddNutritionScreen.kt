package com.example.nutritionalcounter.ui.nutrition_add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.nutritionalcounter.data.db.Nutrition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNutritionScreen(
    onSaveClick: (Nutrition) -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var kilojoule by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fibre by remember { mutableStateOf("") }
    var netCarbs by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Új tápérték hozzáadása") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Vissza")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Étel / Alapanyag neve") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = kilojoule,
                onValueChange = { kilojoule = it },
                label = { Text("Energia (KJ)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fat,
                onValueChange = { fat = it },
                label = { Text("Zsír (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = protein,
                onValueChange = { protein = it },
                label = { Text("Fehérje (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = carbs,
                onValueChange = { carbs = it },
                label = { Text("Szénhidrát (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fibre,
                onValueChange = { fibre = it },
                label = { Text("Rost (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = netCarbs,
                onValueChange = { netCarbs = it },
                label = { Text("Nettó szénhidrát (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = unit,
                onValueChange = { unit = it },
                label = { Text("Mértékegység (g/ml)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val item = Nutrition(
                        name = name,
                        kilojoule = kilojoule.toDoubleOrNull() ?: 0.0,
                        fat = fat.toDoubleOrNull() ?: 0.0,
                        protein = protein.toDoubleOrNull() ?: 0.0,
                        carbs = carbs.toDoubleOrNull() ?: 0.0,
                        fibre = fibre.toDoubleOrNull() ?: 0.0,
                        netCarbs = netCarbs.toDoubleOrNull() ?:0.0,
                        unit = unit
                    )
                    onSaveClick(item)
                },
                enabled = name.isNotBlank()
            ) {
                Text("Mentés")
            }
        }
    }
}