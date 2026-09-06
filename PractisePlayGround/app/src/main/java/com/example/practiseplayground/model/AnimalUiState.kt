package com.example.practiseplayground.model

import com.example.practiseplayground.data.animal.Animal
import com.example.practiseplayground.data.animal.Species

sealed class AnimalUiState {

    object Loading: AnimalUiState()

    data class Success(
        val animals: List<Animal>,
        val activeFilter: Species = Species.ALL,
    ): AnimalUiState()

    data class Error(val error: String): AnimalUiState()

}