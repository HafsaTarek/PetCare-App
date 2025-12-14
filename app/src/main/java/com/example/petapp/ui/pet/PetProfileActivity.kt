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
                    onBack = { finish() },
                    onMoodClick = { /* navigate to MoodTrackerActivity */ },
                    onVaccinationsClick = { /* navigate to VaccinationListActivity */ }
                )
            }
        }
    }
}

