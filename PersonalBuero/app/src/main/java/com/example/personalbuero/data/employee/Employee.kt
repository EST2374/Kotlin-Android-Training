package com.example.personalbuero.data.employee

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Employee(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val salary: Double,
    val offDays: List<OffDay> = listOf(OffDay.SA, OffDay.SO)
)