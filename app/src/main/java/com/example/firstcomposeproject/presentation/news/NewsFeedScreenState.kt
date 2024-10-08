package com.example.firstcomposeproject.presentation.news

import com.example.firstcomposeproject.domain.entities.FeedPost

sealed class NewsFeedScreenState {

    data object Initial : NewsFeedScreenState()
    data object Loading : NewsFeedScreenState()
    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()

}