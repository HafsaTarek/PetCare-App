package com.example.petapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petapp.data.entity.Vaccination
import com.example.petapp.data.repository.VaccinationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VaccinationViewModel(private val repository: VaccinationRepository) : ViewModel() {

    val allVaccinations: StateFlow<List<Vaccination>> =
        repository.getAllVaccinations().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getVaccinationsForPet(petId: Int): StateFlow<List<Vaccination>> =
        repository.getVaccinationsForPet(petId).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addVaccination(vaccination: Vaccination) = viewModelScope.launch { repository.insertVaccination(vaccination) }

    fun updateVaccination(vaccination: Vaccination) = viewModelScope.launch { repository.updateVaccination(vaccination) }

    fun deleteVaccination(vaccination: Vaccination) = viewModelScope.launch { repository.deleteVaccination(vaccination) }
}
