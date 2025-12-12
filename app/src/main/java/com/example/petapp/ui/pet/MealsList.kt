package com.example.petapp.ui.pet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petapp.data.entity.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsListScreen(
    meals: List<Meal>,
    onEdit: (Meal) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meals") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            items(meals) { meal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Name: ${meal.mealName}")
                        Text("Quantity: ${meal.quantity}")
                        Text("Time: ${meal.time}")
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(onClick = { onEdit(meal) }) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }
}
