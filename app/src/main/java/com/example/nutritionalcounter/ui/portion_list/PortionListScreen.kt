package com.example.nutritionalcounter.ui.portion_list


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.nutritionalcounter.data.db.PortionWithNutrition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortionListScreen(
    portionList: List<PortionWithNutrition>,
    searchQuery: String,                         // UI állapot átadása
    onSearchQueryChange: (String) -> Unit,       // Keresési szöveg változása
    onAddPortionClick: () -> Unit,
    onPortionClick: (PortionWithNutrition) -> Unit,
    onDeletePortionClick: (PortionWithNutrition) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adagok") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPortionClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Elfogyasztott adag"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Keresőmező
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                placeholder = { Text("Keresés dátum alapján...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    AnimatedVisibility(visible = searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Törlés")
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            // Lista vagy Üres állapot
            if (portionList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isEmpty()) "Még nincs elfogyasztott adag." else "Nincs találat.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 88.dp) // FAB helye miatt
                ) {
                    items(
                        items = portionList,
                        key = { it.portion.id }
                    ) { portionWithNutrition ->
                        PortionItemCard(
                            portionWithNutrition = portionWithNutrition,
                            onClick = { onPortionClick(portionWithNutrition) },
                            onDeleteClick = { onDeletePortionClick(portionWithNutrition) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortionItemCard(
    portionWithNutrition: PortionWithNutrition,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = portionWithNutrition.nutrition.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MacroText(label = "", value = portionWithNutrition.portion.amount)
                }
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Törlés",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
@Composable
private fun MacroText(
    label: String,
    value: Double,
    isBold: Boolean = false
) {
    val textToShow = if (label.isBlank()) "${value}g" else "$label: ${value}g"

    Text(
        text = textToShow,
        fontSize = 12.sp,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
        color = if (isBold) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    )
}