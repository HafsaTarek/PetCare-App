package com.example.petapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petapp.data.entity.Meal
import com.example.petapp.data.repository.MealRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MealViewModel(private val repository: MealRepository) : ViewModel() {

    val allMeals: StateFlow<List<Meal>> =
        repository.getAllMeals().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getMealsForPet(petId: Int): StateFlow<List<Meal>> =
        repository.getMealsForPet(petId).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addMeal(meal: Meal) = viewModelScope.launch { repository.insertMeal(meal) }

    fun deleteMeal(meal: Meal) = viewModelScope.launch { repository.deleteMeal(meal) }

    fun updateMeal(meal: Meal) = viewModelScope.launch { repository.updateMeal(meal) }
}
