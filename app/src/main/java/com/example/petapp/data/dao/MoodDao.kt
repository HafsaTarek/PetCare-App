package com.example.petapp.data.dao

import androidx.room.*
import com.example.petapp.data.entity.Mood
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: Mood)

    @Update
    suspend fun updateMood(mood: Mood)

    @Delete
    suspend fun deleteMood(mood: Mood)

    @Query("SELECT * FROM moods ORDER BY date DESC")
    fun getAllMoods(): Flow<List<Mood>>

    @Query("SELECT * FROM moods WHERE petId = :petId ORDER BY date DESC")
    fun getMoodsForPet(petId: Int): Flow<List<Mood>>

    @Query("SELECT * FROM moods WHERE id = :id LIMIT 1")
    suspend fun getMoodById(id: Int): Mood?
}
