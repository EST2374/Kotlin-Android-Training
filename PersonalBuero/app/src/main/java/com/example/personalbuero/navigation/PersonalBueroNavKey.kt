package com.example.personalbuero.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface PersonalBueroNavKey: NavKey {


    @Serializable
    data object PersonalBueroList: PersonalBueroNavKey

    @Serializable
    data class PersonalDetail(val id: String): PersonalBueroNavKey


}