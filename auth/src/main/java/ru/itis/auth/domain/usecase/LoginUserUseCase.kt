package ru.itis.auth.domain.usecase

import ru.itis.auth.data.repository.AuthRepository

class LoginUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return repository.loginUser(username, password)
    }
}