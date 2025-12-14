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
import com.example.petapp.ui.auth.LoginScreen
import com.example.petapp.ui.auth.RegisterScreen
import com.example.petapp.ui.pet.*
import com.example.petapp.ui.screens.MoodTrackerScreen
import com.example.petapp.ui.screens.VaccinationListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController = navController)
        }

        composable("register") {
            RegisterScreen(navController = navController)
        }

        composable("pets") {
            PetListScreen(navController = navController)
        }

        composable("petProfile/{petId}") { backStack ->
            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable
            PetProfileScreenWrapper(navController, petId)
        }

        composable("moodTracker/{petId}") { backStack ->
            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable
            MoodTrackerScreenWrapper(navController, petId)
        }

        composable("vaccinations/{petId}") { backStack ->
            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable
            val context = LocalContext.current
            val db = PetDatabase.getInstance(context)
            val repo = VaccinationRepository(db.vaccinationDao())
            val factory = VaccinationViewModelFactory(repo)
            val vm: VaccinationViewModel = viewModel(factory = factory)
            VaccinationListScreen(petId = petId, viewModel = vm, navController = navController)
        }

    }
}

@Composable
fun PetProfileScreenWrapper(navController: NavHostController, petId: Int) {
    PetProfileScreen(
        petId = petId,
        onBack = { navController.popBackStack() },
        onMoodClick = { navController.navigate("moodTracker/$petId") },
        onVaccinationsClick = { navController.navigate("vaccinations/$petId") }
    )
}

@Composable
fun MoodTrackerScreenWrapper(navController: NavHostController, petId: Int) {
    MoodTrackerScreen(
        petId = petId,
        onBack = { navController.popBackStack() }
    )
}
