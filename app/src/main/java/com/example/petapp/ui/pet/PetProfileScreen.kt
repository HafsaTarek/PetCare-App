package com.example.petapp.ui.pet

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
    onAddMealClick: () -> Unit,
    onEditMealClick: (Int) -> Unit,
    onMoodClick: () -> Unit,
    onVaccinationsClick: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { PetDatabase.getInstance(context) }

    val pet by produceState<Pet?>(initialValue = null, petId) {
        value = db.petDao().getPetById(petId)
    }

    val meals by produceState(initialValue = emptyList<Meal>(), petId) {
        db.mealDao().getMealsForPet(petId).collect { value = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pet Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
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

            Button(onClick = onAddMealClick, modifier = Modifier.fillMaxWidth()) {
                Text("Add Meal")
            }

            Spacer(modifier = Modifier.height(16.dp))

            MealsList(meals = meals, onEditMealClick = onEditMealClick)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onMoodClick, modifier = Modifier.fillMaxWidth()) {
                Text("Mood Tracker")
            }

            Spacer(modifier = Modifier.height(16.dp))

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
