package com.example.practiseplayground.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer
import com.example.practiseplayground.data.animal.Animal
import com.example.practiseplayground.data.repository.AnimalShelterNetworkImplementation
import com.example.practiseplayground.data.repository.AnimalShelterRepository
import com.example.practiseplayground.retrofit.RetrofitTodo
import com.example.practiseplayground.retrofit.Todo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AnimalDetailsViewModel(
    private val animalShelterRepository: AnimalShelterRepository = AnimalShelterNetworkImplementation,
    private val animalId: String,
): ViewModel() {

    val getAnimal: StateFlow<Animal?> = animalShelterRepository
        .getAnimals()
        .map { animals -> animals.find { it.id == animalId } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    suspend fun getTodo(): Todo {
        return RetrofitTodo.todoApi.getTodo(1)
    }

}



    fun AnimalDetailsViewModelFactory(animalId: String) = viewModelFactory {
        initializer {
            AnimalDetailsViewModel(
                animalId = animalId,
                animalShelterRepository = AnimalShelterNetworkImplementation
            )
        }
    }
