package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petapp.data.entity.Vaccination

@Composable
fun VaccinationFormDialog(
    vaccination: Vaccination?,
    petId: Int,
    onDismiss: () -> Unit,
    onSave: (Vaccination) -> Unit
) {
    var name by remember { mutableStateOf(vaccination?.name ?: "") }
    var date by remember { mutableStateOf(vaccination?.date?.toString() ?: "") }
    var nextDue by remember { mutableStateOf(vaccination?.nextDue?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (vaccination == null) "Add Vaccination" else "Edit Vaccination")
        },
        text = {
            Column {
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
                    label = { Text("Next Due (timestamp, optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
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
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
