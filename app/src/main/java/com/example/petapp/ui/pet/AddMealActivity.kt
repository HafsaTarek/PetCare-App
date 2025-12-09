package com.example.petapp.ui.pet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AddMealActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mealId = intent.getIntExtra("MEAL_ID", -1)
        val petId = intent.getIntExtra("PET_ID", -1)

        setContent {
            AddMealScreen(petId = petId, mealId = if(mealId != -1) mealId else null)
        }
    }
}
