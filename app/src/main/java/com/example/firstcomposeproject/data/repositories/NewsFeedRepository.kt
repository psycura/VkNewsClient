package com.example.firstcomposeproject.data.repositories

import android.app.Application
import android.util.Log
import com.example.firstcomposeproject.data.mappers.NewsFeedMapper
import com.example.firstcomposeproject.data.network.ApiFactory
import com.example.firstcomposeproject.data.network.ApiService
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.PostComment
import com.example.firstcomposeproject.domain.StatisticItem
import com.example.firstcomposeproject.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import java.lang.IllegalStateException

class NewsFeedRepository private constructor(application: Application) {
    companion object {

        @Volatile
        private var instance: NewsFeedRepository? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: NewsFeedRepository(application).also { instance = it }
            }
    }

    private val service = ApiService(ApiFactory.client)
    private val mapper = NewsFeedMapper()

    private val vkStorage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(vkStorage)

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null


    suspend fun loadNewsFeed(): List<FeedPost> {
        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response = if (startFrom == null) {
            service.getNewsFeed(getAccessToken())
        } else {
            service.getNewsFeed(getAccessToken(), startFrom)
        }

        nextFrom = response.newsFeedContent.nextFrom

        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)

        return feedPosts
    }

    fun getPostById(feedPostId: Long): FeedPost? {
        for (post in feedPosts) {
            Log.d("NewsFeedRepository","Post: ${post.id}")
        }

        return feedPosts.firstOrNull { it.id == feedPostId }
    }

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val response = service.getComments(
            token = getAccessToken(),
            itemId = feedPost.id,
            ownerId = feedPost.communityId
        )

        return mapper.mapResponseToComments(response)
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

    suspend fun ignoreItem(feedPost: FeedPost) {
        service.ignoreItem(
            token = getAccessToken(),
            itemId = feedPost.id,
            ownerId = feedPost.communityId
        )

        _feedPosts.remove(feedPost)

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