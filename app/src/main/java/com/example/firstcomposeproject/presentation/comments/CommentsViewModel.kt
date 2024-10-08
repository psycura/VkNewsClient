package com.example.firstcomposeproject.presentation.comments

import androidx.lifecycle.ViewModel
import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.usecases.GetCommentsUseCase
import com.example.firstcomposeproject.domain.usecases.GetPostUseCase
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinViewModel
class CommentsViewModel(
    @InjectedParam
    feedPostId: Long
) : ViewModel(), KoinComponent {

    private val getCommentsUseCase: GetCommentsUseCase by inject()
    private val getPostUseCase: GetPostUseCase by inject()

    private var feedPost: FeedPost? = null

    val screenState = getCommentsUseCase(feedPostId)
        .map { CommentsScreenState.Comments(feedPost!!, it) as CommentsScreenState }

    init {
        feedPost = getPostUseCase(feedPostId)
    }


}