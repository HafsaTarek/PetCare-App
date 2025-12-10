package com.example.petapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petapp.data.database.PetDatabase
import com.example.petapp.data.repository.VaccinationRepository
import com.example.petapp.data.viewmodel.VaccinationViewModel
import com.example.petapp.data.viewmodel.VaccinationViewModelFactory
import com.example.petapp.ui.screens.MoodTrackerScreen
import com.example.petapp.ui.screens.VaccinationListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "mood"
    ) {

        // Mood screen
        composable("mood") {
            MoodTrackerScreen(navController)
        }

        // Vaccinations screen
        composable("vaccinations/{petId}") { backStack ->

            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable

            val context = LocalContext.current
            val db = PetDatabase.getInstance(context) // FIXED
            val repo = VaccinationRepository(db.vaccinationDao())
            val factory = VaccinationViewModelFactory(repo)

            val vm: VaccinationViewModel = viewModel(
                factory = factory,
                modelClass = VaccinationViewModel::class.java
            )

            VaccinationListScreen(
                petId = petId,
                viewModel = vm,
                navController = navController
            )
        }
    }
}
