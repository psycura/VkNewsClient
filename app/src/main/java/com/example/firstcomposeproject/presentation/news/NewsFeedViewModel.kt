package com.example.firstcomposeproject.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.data.repositories.NewsFeedRepositoryImpl
import com.example.firstcomposeproject.domain.entities.FeedPost
import com.example.firstcomposeproject.domain.usecases.ChangeLikeStatusUseCase
import com.example.firstcomposeproject.domain.usecases.DeletePostUseCase
import com.example.firstcomposeproject.domain.usecases.GetNewsFeedUseCase
import com.example.firstcomposeproject.domain.usecases.LoadNextDataUseCase
import com.example.firstcomposeproject.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl.getInstance(application)
    private val getNewsFeedUseCase = GetNewsFeedUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deletePostUseCase = DeletePostUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)

    private val newsFeedFlow = getNewsFeedUseCase()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("NewsFeedViewModel", "Exception caught", throwable)

    }

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()

    val screenState = newsFeedFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun loadNextPosts() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    newsFeedFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun deletePost(post: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(post)
        }
    }
}