package com.example.training.repositories

import com.example.training.model.User

interface UserRepository {
    suspend fun getUserData(): User
}

class UserRepositoryImpl: UserRepository {
    override suspend fun getUserData(): User {
        val user = User("Jakob",21)
        return user
    }

}