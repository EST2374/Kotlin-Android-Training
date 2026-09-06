package com.example.practiseplayground.data.animal

import kotlinx.serialization.Serializable
import java.util.UUID

// TODO(add animalImage)

@Serializable
data class Animal(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: Int,
    val species: Species = Species.DOG,
    val status: AnimalStatus = AnimalStatus.AVAILABLE
)