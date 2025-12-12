package com.example.petapp.ui.pet

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Pet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditPetActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = PetDatabase.getInstance(this)
        val petId = intent.getIntExtra("PET_ID", -1)

        setContent {
            var name by remember { mutableStateOf("") }
            var type by remember { mutableStateOf("") }
            var age by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            var imageUrl by remember { mutableStateOf("") }

            // Load pet data if editing
            LaunchedEffect(petId) {
                if (petId != -1) {
                    val existingPet = db.petDao().getPetById(petId)
                    existingPet?.let {
                        name = it.name
                        type = it.type
                        age = it.age.toString()
                        description = it.description
                        imageUrl = it.imageUrl ?: ""
                    }
                }
            }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = if (petId == -1) "Add Pet" else "Edit Pet")
                        },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
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
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = type,
                        onValueChange = { type = it },
                        label = { Text("Type (dog, cat...)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Age") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("Image URL (optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (name.isBlank() || type.isBlank() || age.isBlank()) {
                                Toast.makeText(this@AddEditPetActivity, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            val pet = Pet(
                                id = if (petId != -1) petId else 0,
                                name = name,
                                type = type,
                                age = age.toIntOrNull() ?: 0,
                                description = description,
                                imageUrl = if (imageUrl.isBlank()) null else imageUrl
                            )

                            CoroutineScope(Dispatchers.IO).launch {
                                db.petDao().insertPet(pet)
                                runOnUiThread {
                                    Toast.makeText(this@AddEditPetActivity, "Pet saved!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (petId == -1) "Add Pet" else "Save Changes")
                    }
                }
            }
        }
    }
}
