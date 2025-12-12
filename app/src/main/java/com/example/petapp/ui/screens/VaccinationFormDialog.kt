package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petapp.data.entity.Vaccination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaccinationFormDialog(
    vaccination: Vaccination?,
    petId: Int,
    onDismiss: () -> Unit,
    onSave: (Vaccination) -> Unit,
    onBack: () -> Unit   // <-- Added back callback
) {
    var name by remember { mutableStateOf(vaccination?.name ?: "") }
    var date by remember { mutableStateOf(vaccination?.date?.toString() ?: "") }
    var nextDue by remember { mutableStateOf(vaccination?.nextDue?.toString() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (vaccination == null) "Add Vaccination" else "Edit Vaccination") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (timestamp)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nextDue,
                onValueChange = { nextDue = it },
                label = { Text("Next Due (optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }

                Button(onClick = {
                    val newVaccination = Vaccination(
                        id = vaccination?.id ?: 0,
                        petId = petId,
                        name = name,
                        date = date.toLongOrNull() ?: 0L,
                        nextDue = nextDue.toLongOrNull()
                    )
                    onSave(newVaccination)
                    onDismiss()
                }) {
                    Text("Save")
                }
            }
        }
    }
}
