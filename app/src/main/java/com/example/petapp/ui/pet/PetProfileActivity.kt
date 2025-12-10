package com.example.petapp.ui.pet

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Meal

@Composable
fun PetProfileScreen(petId: Int) {
    val context = LocalContext.current
    val db = PetDatabase.getInstance(context)

    // Get pet data
    val petFlow = db.petDao().getPetById(petId)
    val pet by petFlow.collectAsState(initial = null)

    // Get meals for this pet
    val mealsFlow = db.mealDao().getMealsForPet(petId)
    val meals by mealsFlow.collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Header with pet info
        pet?.let { PetProfileHeader(it) }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Meal Button
        Button(onClick = {
            context.startActivity(
                Intent(context, AddMealActivity::class.java).apply {
                    putExtra("PET_ID", petId)
                }
            )
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Meal")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display meals using MealsList
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
