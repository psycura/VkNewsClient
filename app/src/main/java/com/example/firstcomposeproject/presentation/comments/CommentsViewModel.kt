package com.example.firstcomposeproject.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.data.repositories.NewsFeedRepository
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.PostComment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    feedPostId: Long
) : ViewModel() {
    private val repository = NewsFeedRepository.getInstance(application)

    private val _screenState = MutableStateFlow<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: StateFlow<CommentsScreenState> = _screenState

    init {
        _screenState.value = CommentsScreenState.Loading
        loadComments(feedPostId)
    }

    private fun loadComments(feedPostId: Long) {
        val post = repository.getPostById(feedPostId)

        if (post == null) {
            _screenState.value = CommentsScreenState.Error("Post with ID: $feedPostId not found")
            return
        }

        viewModelScope.launch {
            val comments = repository.getComments(feedPost = post)

            _screenState.value = CommentsScreenState.Comments(post, comments)
        }

    }

}