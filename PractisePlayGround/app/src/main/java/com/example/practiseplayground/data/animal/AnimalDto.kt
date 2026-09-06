package com.example.practiseplayground.data.animal

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
class AnimalDto(
    val name: String,
    val age: Int,
    val species: String,
    val status: String,
    val id: String
)