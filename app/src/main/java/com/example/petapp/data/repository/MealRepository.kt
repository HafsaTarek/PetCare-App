package com.example.petapp.data.repository

import com.example.petapp.data.dao.MealDao
import com.example.petapp.data.entity.Meal
import kotlinx.coroutines.flow.Flow

class MealRepository(private val dao: MealDao) {

    fun getAllMeals(): Flow<List<Meal>> = dao.getAllMeals()

    fun getMealsForPet(petId: Int): Flow<List<Meal>> = dao.getMealsForPet(petId)

    suspend fun insertMeal(meal: Meal) = dao.insertMeal(meal)

    suspend fun deleteMeal(meal: Meal) = dao.deleteMeal(meal)

    suspend fun updateMeal(meal: Meal) = dao.updateMeal(meal)
}
