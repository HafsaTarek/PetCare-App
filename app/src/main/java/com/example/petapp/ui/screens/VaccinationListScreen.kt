package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.data.entity.Vaccination
import com.example.petapp.data.viewmodel.VaccinationViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaccinationListScreen(
    petId: Int,
    viewModel: VaccinationViewModel,
    navController: NavController
) {
    val vaccinations by viewModel.getVaccinationsForPet(petId).collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var selectedVaccination by remember { mutableStateOf<Vaccination?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Vaccinations") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedVaccination = null
                showDialog = true
            }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(vaccinations) { vaccination ->
                VaccinationItem(
                    vaccination = vaccination,
                    onEdit = {
                        selectedVaccination = it
                        showDialog = true
                    },
                    onDelete = { viewModel.deleteVaccination(it) }
                )
            }
        }
    }

    if (showDialog) {
        VaccinationFormDialog(
            vaccination = selectedVaccination,
            petId = petId,
            onDismiss = { showDialog = false },
            onSave = { v ->
                if (v.id == 0) viewModel.addVaccination(v)
                else viewModel.updateVaccination(v)
                showDialog = false
            }
        )
    }
}

@Composable
fun VaccinationItem(
    vaccination: Vaccination,
    onEdit: (Vaccination) -> Unit,
    onDelete: (Vaccination) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = vaccination.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Date: ${
                        SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                        ).format(Date(vaccination.date))
                    }"
                )
                vaccination.nextDue?.let {
                    Text(
                        text = "Next Due: ${
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                        }"
                    )
                }
            }
            Row {
                IconButton(onClick = { onEdit(vaccination) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDelete(vaccination) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
