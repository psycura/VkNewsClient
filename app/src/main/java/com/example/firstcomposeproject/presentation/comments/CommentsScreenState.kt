package com.example.firstcomposeproject.presentation.comments

import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.entities.PostComment

sealed class CommentsScreenState {

    data object Initial : CommentsScreenState()
    data object Loading : CommentsScreenState()
    data class Error(val errorText: String) : CommentsScreenState()
    data class Comments(val post: FeedPost, val comments: List<PostComment>) : CommentsScreenState()

}