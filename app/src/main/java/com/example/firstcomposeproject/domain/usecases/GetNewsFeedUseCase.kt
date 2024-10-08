package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
class GetNewsFeedUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<List<FeedPost>> {
        return repository.getNewsFeed()
    }

}