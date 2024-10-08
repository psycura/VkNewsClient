package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository

class GetPostUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(feedPostId: Long): FeedPost? {
        return repository.getPostById(feedPostId)
    }
}