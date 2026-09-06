package com.example.personalbuero.data.employee

import kotlinx.serialization.Serializable

@Serializable
enum class OffDay {

    ALL,
    MO,
    DI,
    MI,
    DO,
    FR,
    SA,
    SO

}