package com.example.petapp.ui.pet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class AddMealActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mealId = intent.getIntExtra("MEAL_ID", -1)
        val petId = intent.getIntExtra("PET_ID", -1)

        setContent {
            AddMealScreenWrapper(petId = petId, mealId = if (mealId != -1) mealId else null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealScreenWrapper(petId: Int, mealId: Int?) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (mealId == null) "Add Meal" else "Edit Meal") },
                navigationIcon = {
                    IconButton(onClick = { (context as? ComponentActivity)?.finish() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AddMealScreen(
                petId = petId,
                mealId = mealId
            )
        }
    }
}

