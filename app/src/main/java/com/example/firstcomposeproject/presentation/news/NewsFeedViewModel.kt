package com.example.firstcomposeproject.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.data.repositories.NewsFeedRepository
import com.example.firstcomposeproject.domain.FeedPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow<NewsFeedScreenState>(NewsFeedScreenState.Initial)
    val screenState: StateFlow<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository.getInstance(application)

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadPosts()
    }

    private fun loadPosts() {

        viewModelScope.launch {
            val feedPosts = repository.loadNewsFeed()
            _screenState.value = NewsFeedScreenState.Posts(feedPosts)
        }
    }

    fun loadNextPosts() {
        _screenState.value =
            NewsFeedScreenState.Posts(
                repository.feedPosts,
                nextDataIsLoading = true
            )

        loadPosts()
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

    fun deletePost(post: FeedPost) {
        viewModelScope.launch {
            repository.ignoreItem(post)
            _screenState.value = NewsFeedScreenState.Posts(repository.feedPosts)
        }

    }
}