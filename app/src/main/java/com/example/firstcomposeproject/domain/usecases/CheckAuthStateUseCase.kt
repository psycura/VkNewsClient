package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository

class CheckAuthStateUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() {
        return repository.checkAuthState()
    }
}