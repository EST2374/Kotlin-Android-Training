package com.example.personalbuero.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalbuero.data.employee.Employee
import com.example.personalbuero.data.employee.OffDay
import com.example.personalbuero.data.repository.PersonalBueroImplementation
import com.example.personalbuero.data.repository.PersonalBueroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonalBueroViewModel(
    private val employeeRepository: PersonalBueroRepository = PersonalBueroImplementation
): ViewModel() {

    private val _toFilterByOffDay = MutableStateFlow(OffDay.ALL)


    val uiState: StateFlow<PersonalBueroUiState> = combine(
        employeeRepository.getEmployees(),
        _toFilterByOffDay
    ) { employees, filter ->

        val filterEmployee = employees.filter { employee ->
            val matchFilter = when(filter) {
                OffDay.ALL -> true
                OffDay.MO -> employee.offDays.contains(OffDay.MO)
                OffDay.DI -> employee.offDays.contains(OffDay.DI)
                OffDay.MI -> employee.offDays.contains(OffDay.MI)
                OffDay.DO -> employee.offDays.contains(OffDay.DO)
                OffDay.FR -> employee.offDays.contains(OffDay.FR)
                OffDay.SA -> employee.offDays.contains(OffDay.SA)
                OffDay.SO -> employee.offDays.contains(OffDay.SO)
            }
            matchFilter
        }

        PersonalBueroUiState.Success(
            employees = filterEmployee,
            activeFilter = filter
        ) as PersonalBueroUiState

    }.catch { emit(PersonalBueroUiState.Error(it.message ?: "Unknown Error")) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PersonalBueroUiState.Loading
        )

    fun addEmployee(employee: Employee) {
        viewModelScope.launch {
            employeeRepository.addEmployees(employee)
        }
    }

    fun removeEmployeeById(id: String) {
        viewModelScope.launch {
            employeeRepository.removeEmployeeById(id)
        }
    }

    fun filterByOffDay(offDay: OffDay) {
        _toFilterByOffDay.value = offDay
    }


}