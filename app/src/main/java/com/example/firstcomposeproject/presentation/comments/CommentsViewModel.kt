package com.example.firstcomposeproject.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.firstcomposeproject.data.repositories.NewsFeedRepositoryImpl
import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.usecases.GetCommentsUseCase
import com.example.firstcomposeproject.domain.usecases.GetPostUseCase
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    application: Application,
    feedPostId: Long
) : ViewModel() {
    private val repository = NewsFeedRepositoryImpl.getInstance(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)
    private val getPostUseCase = GetPostUseCase(repository)

    private var feedPost: FeedPost? = null

    val screenState = getCommentsUseCase(feedPostId)
        .map { CommentsScreenState.Comments(feedPost!!, it) as CommentsScreenState }

    init {
        feedPost = getPostUseCase(feedPostId)
    }


}