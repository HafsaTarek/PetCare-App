package com.example.petapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petapp.data.entity.HealthSummary
import com.example.petapp.data.repository.HealthSummaryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HealthSummaryViewModel(private val repository: HealthSummaryRepository) : ViewModel() {

    val allSummaries: StateFlow<List<HealthSummary>> =
        repository.getAllSummaries().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getSummariesForPet(petId: Int): StateFlow<List<HealthSummary>> =
        repository.getSummariesForPet(petId).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addSummary(summary: HealthSummary) = viewModelScope.launch { repository.insertSummary(summary) }

    fun updateSummary(summary: HealthSummary) = viewModelScope.launch { repository.updateSummary(summary) }

    fun deleteSummary(summary: HealthSummary) = viewModelScope.launch { repository.deleteSummary(summary) }
}
