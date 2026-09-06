package com.example.personalbuero.data.remote

import com.example.personalbuero.data.employee.OffDay
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeDto(
    val id: String,
    val name: String,
    val salary: Double,
    val offDays: List<OffDay>
)