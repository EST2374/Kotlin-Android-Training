package com.example.practiseplayground.data.repository

import com.example.practiseplayground.data.animal.Animal
import com.example.practiseplayground.data.animal.AnimalDto
import com.example.practiseplayground.retrofit.RetrofitAnimal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

object AnimalShelterNetworkImplementation: AnimalShelterRepository {

    private val _onRefresh = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)

    override fun getAnimals(): Flow<List<Animal>> {
        return _onRefresh.onStart {
            emit(Unit)
        }.flatMapLatest{ flow { emit(RetrofitAnimal.animalsApi.getAnimals()) } }
    }

    override suspend fun addAnimal(animal: AnimalDto) {
        RetrofitAnimal.animalsApi.addAnimal(animal)
    }

    override suspend fun removeAnimalById(id: String) {
        RetrofitAnimal.animalsApi.removeAnimalById(id)
    }

    override fun onRefresh() {
        _onRefresh.tryEmit(Unit)
    }
}