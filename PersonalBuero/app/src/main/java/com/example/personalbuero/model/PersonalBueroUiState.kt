package com.example.personalbuero.model

import com.example.personalbuero.data.employee.Employee
import com.example.personalbuero.data.employee.OffDay

sealed class PersonalBueroUiState {

    object Loading: PersonalBueroUiState()

    data class Success(
        val employees: List<Employee>,
        val activeFilter: OffDay = OffDay.ALL
    ): PersonalBueroUiState()

    data class Error(val error: String): PersonalBueroUiState()

}