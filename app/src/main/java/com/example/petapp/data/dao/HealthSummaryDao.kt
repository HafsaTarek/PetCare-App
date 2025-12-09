package com.example.petapp.data.dao

import androidx.room.*
import com.example.petapp.data.entity.HealthSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: HealthSummary)

    @Update
    suspend fun updateSummary(summary: HealthSummary)

    @Delete
    suspend fun deleteSummary(summary: HealthSummary)

    @Query("SELECT * FROM health_summaries ORDER BY recordedAt DESC")
    fun getAllSummaries(): Flow<List<HealthSummary>>

    @Query("SELECT * FROM health_summaries WHERE petId = :petId ORDER BY recordedAt DESC")
    fun getSummariesForPet(petId: Int): Flow<List<HealthSummary>>

    @Query("SELECT * FROM health_summaries WHERE id = :id LIMIT 1")
    suspend fun getSummaryById(id: Int): HealthSummary?
}
