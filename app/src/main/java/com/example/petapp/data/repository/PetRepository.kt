package com.example.petapp.data.repository

import com.example.petapp.data.dao.PetDao
import com.example.petapp.data.entity.Pet
import kotlinx.coroutines.flow.Flow

class PetRepository(private val dao: PetDao) {

    fun getAllPets(): Flow<List<Pet>> = dao.getAllPets()

    suspend fun getPetById(id: Int): Pet? = dao.getPetById(id)

    suspend fun insertPet(pet: Pet) = dao.insertPet(pet)

    suspend fun updatePet(pet: Pet) = dao.updatePet(pet)

    suspend fun deletePet(pet: Pet) = dao.deletePet(pet)
}
