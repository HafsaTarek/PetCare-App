package com.example.petapp.data.repository

import com.example.petapp.data.dao.HealthSummaryDao
import com.example.petapp.data.entity.HealthSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import kotlinx.coroutines.flow.first

class HealthSummaryRepositoryTest {

    private lateinit var dao: HealthSummaryDao
    private lateinit var repository: HealthSummaryRepository

    @Before
    fun setUp() {
        dao = mock(HealthSummaryDao::class.java)
        repository = HealthSummaryRepository(dao)
    }

    @Test
    fun `getAllSummaries returns correct data`() {
        runBlocking {
            val mockData = listOf(
                HealthSummary(id = 1, petId = 1, weight = 5f, height = 10f, notes = "note", recordedAt = 123L)
            )
            `when`(dao.getAllSummaries()).thenReturn(flowOf(mockData))

            val result = repository.getAllSummaries().first()
            assertEquals(mockData, result)
            verify(dao).getAllSummaries()
        }
    }

    @Test
    fun `getSummariesForPet returns correct data`() {
        runBlocking {
            val petId = 1
            val mockData = listOf(
                HealthSummary(id = 1, petId = petId, weight = 5f, height = 10f, notes = "note", recordedAt = 123L)
            )
            `when`(dao.getSummariesForPet(petId)).thenReturn(flowOf(mockData))

            val result = repository.getSummariesForPet(petId).first()
            assertEquals(mockData, result)
            verify(dao).getSummariesForPet(petId)
        }
    }

    @Test
    fun `getSummaryById returns correct data`() {
        runBlocking {
            val summaryId = 1
            val summary = HealthSummary(id = summaryId, petId = 1, weight = 5f, height = 10f, notes = "note", recordedAt = 123L)
            `when`(dao.getSummaryById(summaryId)).thenReturn(summary)

            val result = repository.getSummaryById(summaryId)
            assertEquals(summary, result)
            verify(dao).getSummaryById(summaryId)
        }
    }

    @Test
    fun `insertSummary calls dao`() {
        runBlocking {
            val summary = HealthSummary(id = 1, petId = 1, weight = 5f, height = 10f, notes = "note", recordedAt = 123L)
            repository.insertSummary(summary)
            verify(dao).insertSummary(summary)
        }
    }

    @Test
    fun `updateSummary calls dao`() {
        runBlocking {
            val summary = HealthSummary(id = 1, petId = 1, weight = 5f, height = 10f, notes = "note", recordedAt = 123L)
            repository.updateSummary(summary)
            verify(dao).updateSummary(summary)
        }
    }

    @Test
    fun `deleteSummary calls dao`() {
        runBlocking {
            val summary = HealthSummary(id = 1, petId = 1, weight = 5f, height = 10f, notes = "note", recordedAt = 123L)
            repository.deleteSummary(summary)
            verify(dao).deleteSummary(summary)
        }
    }
}
