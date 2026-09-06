package com.example.personalbuero.data.repository

import com.example.personalbuero.data.employee.Employee
import com.example.personalbuero.data.remote.EmployeeRetrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object PersonalBueroImplementation: PersonalBueroRepository {

    override fun getEmployees(): Flow<List<Employee>> {
        return flow { emit(EmployeeRetrofit.employeeApi.getEmployees()) }
    }

    override suspend fun addEmployees(employee: Employee) {
        EmployeeRetrofit.employeeApi.addEmployee(employee)
    }

    override suspend fun removeEmployeeById(id: String) {
        EmployeeRetrofit.employeeApi.removeEmployee(id)
    }
}