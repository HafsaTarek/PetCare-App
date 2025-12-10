package com.example.petapp.ui.pet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.petapp.ui.theme.PetAppTheme

class PetProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get petId from intent
        val petId = intent.getIntExtra("PET_ID", 0)

        setContent {
            PetAppTheme {
                PetProfileScreen(petId = petId)
            }
        }
    }
}
