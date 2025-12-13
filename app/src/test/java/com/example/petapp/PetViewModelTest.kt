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
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PetViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: PetRepository
    private lateinit var viewModel: PetViewModel

    @Before
    fun setup() {
        // Set the main dispatcher for unit tests
        Dispatchers.setMain(testDispatcher)

        repository = mock()
        whenever(repository.getAllPets()).thenReturn(flowOf(emptyList()))
        viewModel = PetViewModel(repository)
    }

    @After
    fun tearDown() {
        // Reset main dispatcher after tests
        Dispatchers.resetMain()
    }

    @Test
    fun `addPet calls repository insertPet`() = runTest(testDispatcher) {
        val pet = Pet(1, "Max", "Dog", 3, "Cute dog")
        viewModel.addPet(pet)

        // Advance time to let coroutine run
        testScheduler.advanceUntilIdle()

        verify(repository).insertPet(pet)
    }

    @Test
    fun `deletePet calls repository deletePet`() = runTest(testDispatcher) {
        val pet = Pet(1, "Bella", "Cat", 2, "Lovely cat")
        viewModel.deletePet(pet)

        // Advance time to let coroutine run
        testScheduler.advanceUntilIdle()

        verify(repository).deletePet(pet)
    }
}
