package com.example.practiseplayground.data.repository

import com.example.practiseplayground.data.animal.Animal
import com.example.practiseplayground.data.animal.AnimalDto
import com.example.practiseplayground.data.animal.Species
import com.example.practiseplayground.retrofit.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AnimalShelterRepository {

    fun getAnimals(): Flow<List<Animal>>

    suspend fun addAnimal(animal: AnimalDto)

    suspend fun removeAnimalById(id: String)

    fun onRefresh()
}