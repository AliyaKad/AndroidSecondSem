package ru.itis.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.auth.R
import ru.itis.auth.data.model.User
import ru.itis.auth.domain.usecase.LoginUserUseCase
import ru.itis.auth.domain.usecase.RegisterUserUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun register(username: String, password: String) {
        viewModelScope.launch {
            val success = registerUserUseCase(User(username, password))
            _authState.value = if (success) AuthState.Registered else AuthState.Error(
                context.getString(
                    R.string.username_already_exists
                ))
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val success = loginUserUseCase(username, password)
            _authState.value = if (success) AuthState.LoggedIn else AuthState.Error(
                context.getString(
                    R.string.invalid_credentials
                ))
        }
    }

    sealed class AuthState {
        object Idle : AuthState()
        object Registered : AuthState()
        object LoggedIn : AuthState()
        data class Error(val message: String) : AuthState()
    }
}