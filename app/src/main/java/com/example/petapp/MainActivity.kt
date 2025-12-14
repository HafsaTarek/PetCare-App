package com.example.petapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.petapp.ui.navigation.AppNavGraph
import com.example.petapp.ui.theme.PetAppTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ MUST BE HERE
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            PetAppTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}
