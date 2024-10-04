package com.example.firstcomposeproject.presentation.news

import androidx.lifecycle.ViewModel
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsFeedViewModel : ViewModel() {

    private val _initialPostsList = mutableListOf<FeedPost>().apply {
        repeat(50) {
            add(FeedPost(id = it))
        }
    }

    private val _screenState = MutableStateFlow<NewsFeedScreenState>(NewsFeedScreenState.Posts(_initialPostsList))
    val screenState: StateFlow<NewsFeedScreenState> = _screenState


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