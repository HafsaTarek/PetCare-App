package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Mood
import com.example.petapp.data.repository.MoodRepository
import com.example.petapp.data.viewmodel.MoodViewModel
import com.example.petapp.data.viewmodel.MoodViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodTrackerScreen(navController: NavController) {

    // --- Initialize ViewModel ---
    val context = LocalContext.current
    val dao = PetDatabase.getInstance(context).moodDao()
    val repo = MoodRepository(dao)
    val factory = MoodViewModelFactory(repo)
    val viewModel: MoodViewModel = viewModel(factory = factory)

    val moodList by viewModel.allMoods.collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var moodText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mood Tracker") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(moodList) { mood ->
                    MoodItem(mood = mood, onDelete = { viewModel.deleteMood(mood) })
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Mood") },
            text = {
                OutlinedTextField(
                    value = moodText,
                    onValueChange = { moodText = it },
                    label = { Text("Mood (happy / sad / etc)") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.addMood(
                        Mood(
                            petId = 1, // TODO: replace with real pet id
                            mood = moodText,
                            date = System.currentTimeMillis()
                        )
                    )
                    moodText = ""
                    showDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun MoodItem(mood: Mood, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Mood: ${mood.mood}")
                Text("Date: ${mood.date}")
            }
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}
