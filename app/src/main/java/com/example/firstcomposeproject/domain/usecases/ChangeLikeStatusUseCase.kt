package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository

class ChangeLikeStatusUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        return repository.changeLikeStatus(feedPost)
    }
}