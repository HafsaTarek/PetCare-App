package com.example.petapp.ui.pet

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Meal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealScreen(petId: Int, mealId: Int?) {
    val context = LocalContext.current
    val db = PetDatabase.getInstance(context)

    var mealName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    var mealToEdit by remember { mutableStateOf<Meal?>(null) }

    // Load meal if editing
    LaunchedEffect(mealId) {
        if (mealId != null) {
            mealToEdit = db.mealDao().getMealById(mealId)
            mealToEdit?.let { meal ->
                mealName = meal.mealName
                quantity = meal.quantity
                time = meal.time
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (mealId == null) "Add Meal" else "Edit Meal") },
                navigationIcon = {
                    IconButton(onClick = { (context as? Activity)?.finish() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
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
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = mealName,
                onValueChange = { mealName = it },
                label = { Text("Meal Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Time") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    if (mealToEdit != null) {
                        db.mealDao().updateMeal(
                            mealToEdit!!.copy(
                                mealName = mealName,
                                quantity = quantity,
                                time = time
                            )
                        )
                    } else {
                        db.mealDao().insertMeal(
                            Meal(petId = petId, mealName = mealName, quantity = quantity, time = time)
                        )
                    }
                    (context as Activity).finish()
                }
            }) {
                Text(if (mealToEdit != null) "Update Meal" else "Save Meal")
            }
        }
    }
}
