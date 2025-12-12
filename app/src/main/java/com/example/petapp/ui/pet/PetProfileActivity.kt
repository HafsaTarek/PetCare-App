package com.example.petapp.ui.pet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.petapp.ui.theme.PetAppTheme

class PetProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val petId = intent.getIntExtra("PET_ID", 0)

        setContent {
            PetAppTheme {
                PetProfileScreen(
                    petId = petId,
                    onBack = { finish() }, // <-- handle back arrow
                    onAddMealClick = {
                        // Example: open AddMealActivity
                        val intent = Intent(this, AddMealActivity::class.java)
                        intent.putExtra("PET_ID", petId)
                        startActivity(intent)
                    },
                    onEditMealClick = { mealId ->
                        val intent = Intent(this, AddMealActivity::class.java)
                        intent.putExtra("PET_ID", petId)
                        intent.putExtra("MEAL_ID", mealId)
                        startActivity(intent)
                    },
                    onMoodClick = {
                        // Example: open MoodTrackerScreen via NavController if using Compose Navigation
                    },
                    onVaccinationsClick = {
                        // Example: open VaccinationListScreen
                    }
                )
            }
        }
    }
}
