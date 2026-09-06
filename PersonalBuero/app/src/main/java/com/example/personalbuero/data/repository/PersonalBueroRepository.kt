package com.example.personalbuero.data.repository

import com.example.personalbuero.data.employee.Employee
import kotlinx.coroutines.flow.Flow

interface PersonalBueroRepository {

    fun getEmployees(): Flow<List<Employee>>

    suspend fun addEmployees(employee: Employee)

    suspend fun removeEmployeeById(id: String)


}