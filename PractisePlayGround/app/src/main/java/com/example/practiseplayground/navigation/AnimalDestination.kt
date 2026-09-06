package com.example.practiseplayground.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AnimalDestination: NavKey {

    @Serializable
    data object AnimalList: AnimalDestination

    @Serializable
    data class AnimalDetail(val id: String): AnimalDestination

}