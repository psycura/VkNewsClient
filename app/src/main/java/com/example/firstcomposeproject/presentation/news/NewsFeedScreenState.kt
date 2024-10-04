package com.example.firstcomposeproject.presentation.news

import com.example.firstcomposeproject.domain.FeedPost

sealed class NewsFeedScreenState {

    data object Initial : NewsFeedScreenState()
    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()

}