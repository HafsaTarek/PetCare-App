package com.example.petapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petapp.data.entity.Pet
import com.example.petapp.data.repository.PetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PetViewModel(private val repository: PetRepository) : ViewModel() {

    val pets: StateFlow<List<Pet>> =
        repository.getAllPets().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addPet(pet: Pet) = viewModelScope.launch { repository.insertPet(pet) }

    fun updatePet(pet: Pet) = viewModelScope.launch { repository.updatePet(pet) }

    fun deletePet(pet: Pet) = viewModelScope.launch { repository.deletePet(pet) }

    suspend fun getPetById(id: Int): Pet? = repository.getPetById(id)
}
