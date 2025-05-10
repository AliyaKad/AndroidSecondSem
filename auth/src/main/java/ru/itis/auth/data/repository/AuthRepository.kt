package ru.itis.auth.data.repository

import ru.itis.auth.data.database.AuthDatabase
import ru.itis.auth.data.database.UserEntity
import ru.itis.auth.data.model.User

class AuthRepository(private val database: AuthDatabase) {

    suspend fun registerUser(user: User): Boolean {
        val existingUser = database.authDao().getUserByUsername(user.username)
        return if (existingUser == null) {
            database.authDao().insertUser(UserEntity(user.username, user.password))
            true
        } else {
            false
        }
    }

    suspend fun loginUser(username: String, password: String): Boolean {
        val user = database.authDao().getUserByUsername(username)
        return user?.password == password
    }
}