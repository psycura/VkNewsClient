package com.example.firstcomposeproject.data.repositories

import android.app.Application
import android.util.Log
import com.example.firstcomposeproject.data.mappers.NewsFeedMapper
import com.example.firstcomposeproject.data.network.ApiService
import com.example.firstcomposeproject.domain.entities.AuthState
import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.entities.PostComment
import com.example.firstcomposeproject.domain.entities.StatisticItem
import com.example.firstcomposeproject.domain.entities.StatisticType
import com.example.firstcomposeproject.domain.repositories.NewsFeedRepository
import com.example.firstcomposeproject.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

import org.koin.core.annotation.Single
import java.lang.IllegalStateException

@Single
class NewsFeedRepositoryImpl(
    application: Application,
    private val mapper: NewsFeedMapper,
    private val service: ApiService
) : NewsFeedRepository {
    companion object {
        private const val RETRY_TIMEOUT_MS = 3000L
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val vkStorage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(vkStorage)

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)

        checkAuthStateEvents.collect {
            val currentToken = token

            val loggedIn = currentToken != null && currentToken.isValid

            if (loggedIn) {
                emit(AuthState.Authorized)
            } else {
                emit(AuthState.NotAuthorized)
            }
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)

        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                service.getNewsFeed(getAccessToken())
            } else {
                service.getNewsFeed(getAccessToken(), startFrom)
            }

            nextFrom = response.newsFeedContent.nextFrom

            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)

            emit(feedPosts)
        }
    }
        .retry {
            delay(RETRY_TIMEOUT_MS)
            true
        }

    private val newsFeed: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override fun getNewsFeed(): StateFlow<List<FeedPost>> = newsFeed

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override fun getPostById(feedPostId: Long): FeedPost? {
        for (post in feedPosts) {
            Log.d("NewsFeedRepository", "Post: ${post.id}")
        }

        return feedPosts.firstOrNull { it.id == feedPostId }
    }

    override fun getComments(feedPostId: Long): StateFlow<List<PostComment>> = flow {
        val feedPost = getPostById(feedPostId)

        if (feedPost == null) {
            emit(emptyList())
            return@flow
        }

        val response = service.getComments(
            token = getAccessToken(),
            itemId = feedPost.id,
            ownerId = feedPost.communityId
        )

        emit(mapper.mapResponseToComments(response))
    }.retry {
        delay(RETRY_TIMEOUT_MS)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            service.deleteLike(
                token = getAccessToken(),
                itemId = feedPost.id,
                ownerId = feedPost.communityId
            )
        } else {
            service.setLike(
                token = getAccessToken(),
                itemId = feedPost.id,
                ownerId = feedPost.communityId
            )
        }

        updatePostLikes(feedPost, response.likes.count, !feedPost.isLiked)
    }

    override suspend fun ignoreItem(feedPost: FeedPost) {
        service.ignoreItem(
            token = getAccessToken(),
            itemId = feedPost.id,
            ownerId = feedPost.communityId
        )

        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    private suspend fun updatePostLikes(
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
        refreshedListFlow.emit(feedPosts)

    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

}