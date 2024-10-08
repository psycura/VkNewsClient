package com.example.firstcomposeproject.domain.repositories

import com.example.firstcomposeproject.domain.entities.AuthState
import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.entities.PostComment
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getNewsFeed(): StateFlow<List<FeedPost>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    fun getPostById(feedPostId: Long): FeedPost?

    fun getComments(feedPostId: Long): StateFlow<List<PostComment>>

    suspend fun changeLikeStatus(feedPost: FeedPost)

    suspend fun ignoreItem(feedPost: FeedPost)
}