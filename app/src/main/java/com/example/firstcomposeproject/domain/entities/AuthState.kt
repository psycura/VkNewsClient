package com.example.firstcomposeproject.domain.entities


sealed class AuthState {
    data object Initial: AuthState()
    data object Authorized: AuthState()
    data object NotAuthorized: AuthState()
}