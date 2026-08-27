package com.example.training.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.training.repositories.UserRepository
import com.example.training.repositories.UserRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class UserViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl()
): ViewModel() {

    private val _getUser = MutableStateFlow<User?>(null)
    val getUser: StateFlow<User?> = _getUser.asStateFlow()

    fun getUserData(){
        viewModelScope.launch {
            delay(2000.milliseconds)
            _getUser.value = userRepository.getUserData()
        }
    }

}