package com.example.practiseplayground.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiseplayground.data.animal.Animal
import com.example.practiseplayground.data.animal.AnimalDto
import com.example.practiseplayground.data.animal.Species
import com.example.practiseplayground.data.repository.AnimalShelterNetworkImplementation
import com.example.practiseplayground.data.repository.AnimalShelterRepository
import com.example.practiseplayground.retrofit.RetrofitAnimal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimalShelterViewModel(

    private val animalRepository: AnimalShelterRepository = AnimalShelterNetworkImplementation,

    ): ViewModel() {

    private val _toFilterSpecie = MutableStateFlow(Species.ALL)

    val uiState: StateFlow<AnimalUiState> = combine(
        animalRepository.getAnimals(),
        _toFilterSpecie) {

        allAnimals, filter ->

        val filterSpecies = allAnimals.filter { animal ->
            val matchFilter = when (filter) {
                Species.ALL -> true
                Species.DOG -> animal.species == Species.DOG
                Species.CAT -> animal.species == Species.CAT
                Species.SHEEP -> animal.species == Species.SHEEP
            }

            matchFilter
        }

        AnimalUiState.Success(
            animals = filterSpecies,
            activeFilter = filter
        ) as AnimalUiState

    }.catch { emit(AnimalUiState.Error(it.message ?: "Unknown error")) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AnimalUiState.Loading
        )


    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            val modifiedAnimal = AnimalDto(
                name = animal.name,
                age = animal.age,
                species = animal.species.name,
                status = animal.status.name,
                id = animal.id
            )
            animalRepository.addAnimal(modifiedAnimal)
            animalRepository.onRefresh()
        }
    }

    fun removeAnimal(id: String) {
        viewModelScope.launch {
            animalRepository.removeAnimalById(id)
            animalRepository.onRefresh()
        }
    }

    fun onFilteredSelected(species: Species) {
        _toFilterSpecie.value = species
    }

}