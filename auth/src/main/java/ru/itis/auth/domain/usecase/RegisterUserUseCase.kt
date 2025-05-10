package ru.itis.auth.domain.usecase

import ru.itis.auth.data.model.User
import ru.itis.auth.data.repository.AuthRepository

class RegisterUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(user: User): Boolean {
        return repository.registerUser(user)
    }
}