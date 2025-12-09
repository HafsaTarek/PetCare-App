package com.example.petapp.data.dao

import androidx.room.*
import com.example.petapp.data.entity.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Update
    suspend fun updateMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM meals WHERE petId = :petId ORDER BY id DESC")
    fun getMealsForPet(petId: Int): Flow<List<Meal>>

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<Meal>>

    @Query("SELECT * FROM meals WHERE id = :mealId LIMIT 1")
    suspend fun getMealById(mealId: Int): Meal?
}
