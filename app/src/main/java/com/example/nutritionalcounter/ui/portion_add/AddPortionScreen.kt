package com.example.nutritionalcounter.ui.portion_add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.nutritionalcounter.data.db.Nutrition
import com.example.nutritionalcounter.data.db.Portion
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPortionScreen(
    nutritionList: List<Nutrition>,             // A választható tápértékek/élelmiszerek listája
    onSaveClick: (Portion) -> Unit,             // Mentés esemény, ami az új Portion entitást várja
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // UI Állapotok
    var selectedNutrition by remember { mutableStateOf<Nutrition?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var amountText by remember { mutableStateOf("") }

    // Validáció: csak akkor menthető, ha van kiválasztott élelmiszer és a megadott mennyiség > 0
    val amountValue = amountText.toDoubleOrNull() ?: 0.0
    val isInputValid = selectedNutrition != null && amountValue > 0.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Új adag hozzáadása") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Vissza"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Tápérték / Élelmiszer kiválasztása (DropdownMenu / ExposedDropdownMenuBox)
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedNutrition?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Élelmiszer kiválasztása") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    if (nutritionList.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Nincs elérhető élelmiszer") },
                            onClick = { isDropdownExpanded = false },
                            enabled = false
                        )
                    } else {
                        nutritionList.forEach { nutrition ->
                            DropdownMenuItem(
                                text = { Text(nutrition.name) },
                                onClick = {
                                    selectedNutrition = nutrition
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // 2. Mennyiség megadása (g / ml)
            OutlinedTextField(
                value = amountText,
                onValueChange = { input ->
                    // Csak számokat és egy tizedespontot/vesszőt engedünk beírni
                    if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*$"))) {
                        amountText = input
                    }
                },
                label = { Text("Mennyiség (g / ml)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            // 3. Mentés Gomb
            Button(
                onClick = {
                    val nutrition = selectedNutrition ?: return@Button
                    val newPortion = Portion(
                        id = 0, // Auto-generate ID az adatbázisban
                        nutritionId = nutrition.id,
                        amount = amountValue,
                        date = LocalDate.now()
                    )
                    onSaveClick(newPortion)
                },
                enabled = isInputValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Mentés")
            }
        }
    }
}