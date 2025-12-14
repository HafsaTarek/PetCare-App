package com.example.petapp.ui.pet

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.ui.pet.AddEditPetActivity
@Composable
fun PetListScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val db = remember { PetDatabase.getInstance(context) }
    val pets by db.petDao().getAllPets().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = {
                val intent = Intent(context, AddEditPetActivity::class.java)
                intent.putExtra("PET_ID", -1)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Pet")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(pets) { pet ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate("petProfile/${pet.id}")
                        }
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Text(text = pet.name)
                    }
                }
            }
        }
    }
}


