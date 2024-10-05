package com.example.firstcomposeproject.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.data.repositories.NewsFeedRepository
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow<NewsFeedScreenState>(NewsFeedScreenState.Initial)
    val screenState: StateFlow<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        loadPosts()
    }


    private fun loadPosts() {

        viewModelScope.launch {
            val feedPosts = repository.loadNewsFeed()
            _screenState.value = NewsFeedScreenState.Posts(feedPosts)
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            if (feedPost.isLiked) {
                repository.removeLike(feedPost)
            } else {
                repository.addLike(feedPost)
            }

            _screenState.value = NewsFeedScreenState.Posts(repository.feedPosts)

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