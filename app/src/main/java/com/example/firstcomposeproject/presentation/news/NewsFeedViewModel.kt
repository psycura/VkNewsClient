package com.example.firstcomposeproject.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstcomposeproject.data.mappers.NewsFeedMapper
import com.example.firstcomposeproject.data.network.ApiFactory
import com.example.firstcomposeproject.data.network.ApiService
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.StatisticItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow<NewsFeedScreenState>(NewsFeedScreenState.Initial)
    val screenState: StateFlow<NewsFeedScreenState> = _screenState

    private val service = ApiService(ApiFactory.client)
    private val vkStorage = VKPreferencesKeyValueStorage(application)
    private val mapper = NewsFeedMapper()

    init {
        loadPosts()
    }


    private fun loadPosts() {

        viewModelScope.launch {
            val token = VKAccessToken.restore(vkStorage) ?: return@launch
            val response = service.getNewsFeed(token.accessToken)

            Log.d("NewsFeedViewModel", "loadPosts: $response")
            val feedPosts = mapper.mapResponseToPosts(response)

            _screenState.value = NewsFeedScreenState.Posts(feedPosts)
        }
    }


    fun updatePostStatisticsCount(post: FeedPost, item: StatisticItem) {
        val currentState = _screenState.value

        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()

        val modifiedPostsList = oldPosts.apply {
            replaceAll { oldPost ->
                if (oldPost.id == post.id) {
                    updateStatisticsCount(oldPost, item)
                } else {
                    oldPost
                }
            }
        }

        _screenState.value = NewsFeedScreenState.Posts(modifiedPostsList)
    }


    fun deletePost(post: FeedPost) {
        val currentState = _screenState.value

        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()

        val modifiedPostsList = oldPosts.apply {
            remove(post)
        }

        _screenState.value = NewsFeedScreenState.Posts(modifiedPostsList)
    }

    private fun updateStatisticsCount(post: FeedPost, item: StatisticItem): FeedPost {
        val newStatistics = post.statistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }

        return post.copy(statistics = newStatistics)
    }
}