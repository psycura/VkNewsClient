package com.example.firstcomposeproject.presentation.comments

import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.PostComment

sealed class CommentsScreenState {

    data object Initial : CommentsScreenState()
    data class Comments(val post: FeedPost, val comments: List<PostComment>) : CommentsScreenState()

}