package com.example.petapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.petapp.data.entity.Pet
import com.example.petapp.data.repository.PetRepository
import com.example.petapp.data.viewmodel.PetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class PetRepositoryTest  {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // For LiveData / StateFlow

    private lateinit var repository: PetRepository
    private lateinit var viewModel: PetViewModel

    // Use a test dispatcher to replace Dispatchers.Main
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set Main dispatcher for ViewModel

        repository = mock(PetRepository::class.java)
        `when`(repository.getAllPets()).thenReturn(flowOf(emptyList()))
        viewModel = PetViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher
    }

    @Test
    fun `addPet calls repository insertPet`() = runTest {
        val pet = Pet(1, "Max", "Dog", 3, "Cute dog")
        viewModel.addPet(pet)

        // Advance until all coroutines finish
        testScheduler.advanceUntilIdle()

        verify(repository).insertPet(pet)
    }

    @Test
    fun `deletePet calls repository deletePet`() = runTest {
        val pet = Pet(1, "Bella", "Cat", 2, "Lovely cat")
        viewModel.deletePet(pet)

        testScheduler.advanceUntilIdle()

        verify(repository).deletePet(pet)
    }
}
