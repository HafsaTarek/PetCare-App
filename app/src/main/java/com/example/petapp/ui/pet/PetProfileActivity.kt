package com.example.petapp.ui.pet

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Meal
import com.example.petapp.data.entity.Pet
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale

@Composable
fun PetProfileActivity(petId: Int) {
    val context = LocalContext.current
    val db = remember { PetDatabase.getInstance(context) }

    // Observe the pet data
    val petFlow = remember(petId) { db.petDao().getPetById(petId) }
    val pet by petFlow.collectAsState(initial = null as Pet?)

    // Observe meals for this pet
    val mealsFlow = remember(petId) { db.mealDao().getMealsForPet(petId) }
    val meals by mealsFlow.collectAsState(initial = emptyList<Meal>())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with pet info
        pet?.let { PetProfileHeader(it) }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add a new meal
        Button(
            onClick = {
                context.startActivity(
                    Intent(context, AddMealActivity::class.java).apply {
                        putExtra("PET_ID", petId)
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Meal")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display meals list
        MealsList(meals = meals) { meal ->
            context.startActivity(
                Intent(context, AddMealActivity::class.java).apply {
                    putExtra("PET_ID", petId)
                    putExtra("MEAL_ID", meal.id)
                }
            )
        }
    }
}

@Composable
fun PetProfileHeader(pet: Pet) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Name: ${pet.name}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Age: ${pet.age} years old", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Type: ${pet.type}", style = MaterialTheme.typography.bodyMedium)

        // Display pet image if available
        pet.imageUrl?.let { imageUrl ->
            if (imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "${pet.name} image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun MealsList(meals: List<Meal>, onMealClick: (Meal) -> Unit) {
    LazyColumn {
        items(meals) { meal ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onMealClick(meal) }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = meal.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Calories: ${meal.calories}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
