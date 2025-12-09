package com.example.petapp.ui.pet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petapp.data.entity.Meal

@Composable
fun MealsList(meals: List<Meal>, onEdit: (Meal) -> Unit) {
    LazyColumn {
        items(meals) { meal ->
            Card(modifier = Modifier
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
