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
import com.example.petapp.ui.pet.*
import com.example.petapp.ui.screens.MoodTrackerScreen
import com.example.petapp.ui.screens.VaccinationListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "pets"
    ) {

        // Pet List Screen
        composable("pets") {
            PetListScreen(
                onPetClick = { petId ->
                    navController.navigate("petProfile/$petId")
                }
            )
        }

        // Pet Profile Screen Wrapper
        composable("petProfile/{petId}") { backStack ->
            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable
            PetProfileScreenWrapper(navController = navController, petId = petId)
        }

        // Mood Tracker Screen Wrapper
        composable("moodTracker/{petId}") { backStack ->
            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable
            MoodTrackerScreenWrapper(navController = navController, petId = petId)
        }

        // Vaccinations Screen
        composable("vaccinations/{petId}") { backStack ->
            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable
            val context = LocalContext.current
            val db = PetDatabase.getInstance(context)
            val repo = VaccinationRepository(db.vaccinationDao())
            val factory = VaccinationViewModelFactory(repo)
            val vm: VaccinationViewModel = viewModel(factory = factory)
            VaccinationListScreen(
                petId = petId,
                viewModel = vm,
                navController = navController
            )
        }

        // Add/Edit Meal Screen
        composable("addMeal/{petId}/{mealId?}") { backStack ->
            val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable
            val mealId = backStack.arguments?.getString("mealId")?.toIntOrNull()
            AddMealScreen(petId = petId, mealId = mealId)
        }
    }
}

// ------------------- Wrappers -------------------

@Composable
fun PetProfileScreenWrapper(navController: NavHostController, petId: Int) {
    PetProfileScreen(
        petId = petId,
        onBack = { navController.popBackStack() }, // <-- add this
        onAddMealClick = { navController.navigate("addMeal/$petId") },
        onEditMealClick = { mealId -> navController.navigate("addMeal/$petId/$mealId") },
        onMoodClick = { navController.navigate("moodTracker/$petId") },
        onVaccinationsClick = { navController.navigate("vaccinations/$petId") }
    )
}

@Composable
fun MoodTrackerScreenWrapper(navController: NavHostController, petId: Int) {
    MoodTrackerScreen(
        petId = petId,
        onBack = { navController.popBackStack() } // handle back
    )
}



// package com.example.petapp.ui.navigation

// import androidx.compose.runtime.Composable
// import androidx.compose.ui.platform.LocalContext
// import androidx.lifecycle.viewmodel.compose.viewModel
// import androidx.navigation.NavHostController
// import androidx.navigation.compose.NavHost
// import androidx.navigation.compose.composable
// import com.example.petapp.data.database.PetDatabase
// import com.example.petapp.data.repository.VaccinationRepository
// import com.example.petapp.data.viewmodel.VaccinationViewModel
// import com.example.petapp.data.viewmodel.VaccinationViewModelFactory
// import com.example.petapp.ui.screens.MoodTrackerScreen
// import com.example.petapp.ui.screens.VaccinationListScreen

// @Composable
// fun AppNavGraph(navController: NavHostController) {

//     NavHost(
//         navController = navController,
//         startDestination = "mood"
//     ) {

//         // Mood screen
//         composable("mood") {
//             MoodTrackerScreen(navController)
//         }

//         // Vaccinations screen
//         composable("vaccinations/{petId}") { backStack ->

//             val petId = backStack.arguments?.getString("petId")?.toIntOrNull() ?: return@composable

//             val context = LocalContext.current
//             val db = PetDatabase.getInstance(context) // FIXED
//             val repo = VaccinationRepository(db.vaccinationDao())
//             val factory = VaccinationViewModelFactory(repo)

//             val vm: VaccinationViewModel = viewModel(
//                 factory = factory,
//                 modelClass = VaccinationViewModel::class.java
//             )

//             VaccinationListScreen(
//                 petId = petId,
//                 viewModel = vm,
//                 navController = navController
//             )
//         }
//     }
// }
