package com.example.petapp.ui.pet

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.entity.Pet
import androidx.compose.runtime.collectAsState

@Composable
fun PetListScreen(onPetClick: (Int) -> Unit) {
    val context = LocalContext.current
    val db = remember { PetDatabase.getInstance(context) }
    val pets by remember { db.petDao().getAllPets() }.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Button(
            onClick = { context.startActivity(Intent(context, AddEditPetActivity::class.java)) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Add Pet") }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(pets) { pet ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onPetClick(pet.id) }
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        if (!pet.imageUrl.isNullOrEmpty()) {
                            AsyncImage(
                                model = pet.imageUrl,
                                contentDescription = "${pet.name} image",
                                modifier = Modifier.size(60.dp).clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(pet.name, style = MaterialTheme.typography.titleMedium)
                            Text("${pet.type}, ${pet.age} years old", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
