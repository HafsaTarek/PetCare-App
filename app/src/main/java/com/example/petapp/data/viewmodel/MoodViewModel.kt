package com.example.petapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petapp.data.entity.Mood
import com.example.petapp.data.repository.MoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MoodViewModel(private val repository: MoodRepository) : ViewModel() {

    val allMoods: StateFlow<List<Mood>> =
        repository.getAllMoods().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getMoodsForPet(petId: Int): StateFlow<List<Mood>> =
        repository.getMoodsForPet(petId).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addMood(mood: Mood) = viewModelScope.launch { repository.insertMood(mood) }

    fun updateMood(mood: Mood) = viewModelScope.launch { repository.updateMood(mood) }

    fun deleteMood(mood: Mood) = viewModelScope.launch { repository.deleteMood(mood) }
}
