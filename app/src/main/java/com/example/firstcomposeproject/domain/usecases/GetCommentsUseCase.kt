package com.example.firstcomposeproject.domain.usecases

import com.example.firstcomposeproject.domain.entities.PostComment
import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetCommentsUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(feedPostId: Long): StateFlow<List<PostComment>> {
        return repository.getComments(feedPostId)
    }

}