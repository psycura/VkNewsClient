package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.entities.AuthState
import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
class GetAuthStateUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}