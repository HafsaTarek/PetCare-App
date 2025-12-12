package com.example.petapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.petapp.ui.navigation.AppNavGraph
import com.example.petapp.ui.theme.PetAppTheme
//import com.google.firebase.Firebase
//
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.auth.ktx.auth
//
//val auth = Firebase.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetAppTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}
