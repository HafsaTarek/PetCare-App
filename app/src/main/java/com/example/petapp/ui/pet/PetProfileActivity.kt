package com.example.petapp.ui.pet

import PetProfileHeader
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Meal
import com.example.petapp.data.entity.Pet



@Composable
fun PetProfileActivity(petId: Int) {
    val context = LocalContext.current
    val db = remember { PetDatabase.getInstance(context) }

    // Load Pet (suspend DAO)
    val pet by produceState<Pet?>(initialValue = null, petId) {
        value = db.petDao().getPetById(petId)
    }

    // Load meals
    val meals by produceState(initialValue = emptyList<Meal>(), petId) {
        db.mealDao().getMealsForPet(petId).collect { mealList ->
            value = mealList
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        pet?.let { PetProfileHeader(it) }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Meal Button
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

        // Meals List
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
