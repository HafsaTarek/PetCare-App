package com.example.petapp.ui.pet

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Meal
import com.example.petapp.data.entity.Pet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetProfileScreen(
    petId: Int,
    onBack: () -> Unit,
    onMoodClick: () -> Unit,
    onVaccinationsClick: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { PetDatabase.getInstance(context) }

    // Load pet
    val pet by produceState<Pet?>(initialValue = null, petId) {
        value = db.petDao().getPetById(petId)
    }

    // Load meals
    val meals by produceState(initialValue = emptyList<Meal>(), petId) {
        db.mealDao().getMealsForPet(petId).collect { value = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pet Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            pet?.let { PetProfileHeader(it) }

            Spacer(modifier = Modifier.height(16.dp))

            // Add Meal button
            Button(
                onClick = {
                    val intent = Intent(context, AddMealActivity::class.java)
                    intent.putExtra("PET_ID", petId)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Meal")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Meals list with Edit buttons
            MealsList(meals = meals, onEditMealClick = { mealId ->
                val intent = Intent(context, AddMealActivity::class.java)
                intent.putExtra("PET_ID", petId)
                intent.putExtra("MEAL_ID", mealId)
                context.startActivity(intent)
            })

            Spacer(modifier = Modifier.height(16.dp))

            // Mood Tracker button
            Button(onClick = onMoodClick, modifier = Modifier.fillMaxWidth()) {
                Text("Mood Tracker")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Vaccinations button
            Button(onClick = onVaccinationsClick, modifier = Modifier.fillMaxWidth()) {
                Text("Vaccinations")
            }
        }
    }
}

@Composable
fun MealsList(
    meals: List<Meal>,
    onEditMealClick: (Int) -> Unit
) {
    LazyColumn {
        items(meals) { meal ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Meal: ${meal.mealName}")
                    Text("Quantity: ${meal.quantity}")
                    Text("Time: ${meal.time}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(onClick = { onEditMealClick(meal.id) }) {
                        Text("Edit")
                    }
                }
            }
        }
    }
}
