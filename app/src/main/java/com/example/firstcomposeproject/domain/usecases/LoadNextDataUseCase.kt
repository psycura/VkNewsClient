package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() {
        return repository.loadNextData()
    }
}