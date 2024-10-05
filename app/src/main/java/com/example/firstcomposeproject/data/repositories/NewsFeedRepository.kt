package com.example.firstcomposeproject.data.repositories

import android.app.Application
import com.example.firstcomposeproject.data.mappers.NewsFeedMapper
import com.example.firstcomposeproject.data.network.ApiFactory
import com.example.firstcomposeproject.data.network.ApiService
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.StatisticItem
import com.example.firstcomposeproject.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import java.lang.IllegalStateException

class NewsFeedRepository(application: Application) {

    private val service = ApiService(ApiFactory.client)
    private val mapper = NewsFeedMapper()

    private val vkStorage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(vkStorage)

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()


    suspend fun loadNewsFeed(): List<FeedPost> {
        val response = service.getNewsFeed(getAccessToken())

        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)

        return posts
    }

    suspend fun addLike(feedPost: FeedPost) {
        val response = service.setLike(
            token = getAccessToken(),
            itemId = feedPost.id,
            ownerId = feedPost.communityId
        )

        updatePostLikes(feedPost, response.likes.count, true)

    }

    suspend fun removeLike(feedPost: FeedPost) {
        val response = service.deleteLike(
            token = getAccessToken(),
            itemId = feedPost.id,
            ownerId = feedPost.communityId
        )

        updatePostLikes(feedPost, response.likes.count, false)

    }

    private fun updatePostLikes(
        feedPost: FeedPost,
        likesCount: Int,
        isFavorite: Boolean
    ) {
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, count = likesCount))
        }

        val newFeedPost = feedPost.copy(statistics = newStatistics, isLiked = isFavorite)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newFeedPost
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }


}