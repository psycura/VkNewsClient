package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository
import org.koin.core.annotation.Factory

@Factory
class GetPostUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(feedPostId: Long): FeedPost? {
        return repository.getPostById(feedPostId)
    }
}