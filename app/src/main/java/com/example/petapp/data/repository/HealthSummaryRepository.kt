package com.example.petapp.data.repository

import com.example.petapp.data.dao.HealthSummaryDao
import com.example.petapp.data.entity.HealthSummary
import kotlinx.coroutines.flow.Flow

class HealthSummaryRepository(private val dao: HealthSummaryDao) {

    fun getAllSummaries(): Flow<List<HealthSummary>> = dao.getAllSummaries()

    fun getSummariesForPet(petId: Int): Flow<List<HealthSummary>> = dao.getSummariesForPet(petId)

    suspend fun getSummaryById(id: Int): HealthSummary? = dao.getSummaryById(id)

    suspend fun insertSummary(summary: HealthSummary) = dao.insertSummary(summary)

    suspend fun updateSummary(summary: HealthSummary) = dao.updateSummary(summary)

    suspend fun deleteSummary(summary: HealthSummary) = dao.deleteSummary(summary)
}
