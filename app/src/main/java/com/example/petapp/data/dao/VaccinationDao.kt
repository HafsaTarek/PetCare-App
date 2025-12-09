package com.example.petapp.data.dao

import androidx.room.*
import com.example.petapp.data.entity.Vaccination
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccinationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccination(vaccination: Vaccination)

    @Update
    suspend fun updateVaccination(vaccination: Vaccination)

    @Delete
    suspend fun deleteVaccination(vaccination: Vaccination)

    @Query("SELECT * FROM vaccinations ORDER BY date DESC")
    fun getAllVaccinations(): Flow<List<Vaccination>>

    @Query("SELECT * FROM vaccinations WHERE petId = :petId ORDER BY date DESC")
    fun getVaccinationsForPet(petId: Int): Flow<List<Vaccination>>

    @Query("SELECT * FROM vaccinations WHERE id = :id LIMIT 1")
    suspend fun getVaccinationById(id: Int): Vaccination?
}
