package com.example.petapp.data.repository

import com.example.petapp.data.dao.VaccinationDao
import com.example.petapp.data.entity.Vaccination
import kotlinx.coroutines.flow.Flow

class VaccinationRepository(private val dao: VaccinationDao) {

    fun getAllVaccinations(): Flow<List<Vaccination>> = dao.getAllVaccinations()

    fun getVaccinationsForPet(petId: Int): Flow<List<Vaccination>> = dao.getVaccinationsForPet(petId)

    suspend fun getVaccinationById(id: Int): Vaccination? = dao.getVaccinationById(id)

    suspend fun insertVaccination(vaccination: Vaccination) = dao.insertVaccination(vaccination)

    suspend fun updateVaccination(vaccination: Vaccination) = dao.updateVaccination(vaccination)

    suspend fun deleteVaccination(vaccination: Vaccination) = dao.deleteVaccination(vaccination)
}
