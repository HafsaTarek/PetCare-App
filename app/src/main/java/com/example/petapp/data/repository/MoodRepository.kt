package com.example.petapp.data.repository

import com.example.petapp.data.dao.MoodDao
import com.example.petapp.data.entity.Mood
import kotlinx.coroutines.flow.Flow

class MoodRepository(private val dao: MoodDao) {

    fun getAllMoods(): Flow<List<Mood>> = dao.getAllMoods()

    fun getMoodsForPet(petId: Int): Flow<List<Mood>> = dao.getMoodsForPet(petId)

    suspend fun getMoodById(id: Int): Mood? = dao.getMoodById(id)

    suspend fun insertMood(mood: Mood) = dao.insertMood(mood)

    suspend fun updateMood(mood: Mood) = dao.updateMood(mood)

    suspend fun deleteMood(mood: Mood) = dao.deleteMood(mood)
}
