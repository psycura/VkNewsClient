package com.example.firstcomposeproject.presentation.news

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.ui.theme.FirstComposeProjectTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCommentsClick: (FeedPost) -> Unit,
) {
    val viewModel: NewsFeedViewModel = viewModel()

    val screenState = viewModel.screenState.collectAsState()

    when (val state = screenState.value) {
        is NewsFeedScreenState.Posts -> FeedPosts(
            posts = state.posts,
            modifier = modifier,
            vm = viewModel,
            onCommentsClick = onCommentsClick
        )

        NewsFeedScreenState.Initial -> {}
    }


}

@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    modifier: Modifier = Modifier,
    vm: NewsFeedViewModel,
    onCommentsClick: (FeedPost) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 82.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts, key = { it.id }) { post ->
            val swipeState = rememberSwipeToDismissBoxState()

            if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart && swipeState.progress >= .5f) {
                Log.d("SWIPER", "DELETE")

                vm.deletePost(post)
            }

            SwipeToDismissBox(
                state = swipeState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {}
            ) {
                PostCard(
                    modifier = Modifier.animateItem(),
                    post = post,
                    onShareClick = { vm.updatePostStatisticsCount(post, it) },
                    onViewsClick = { vm.updatePostStatisticsCount(post, it) },
                    onCommentsClick = { onCommentsClick(post) },
                    onLikeClick = { vm.updatePostStatisticsCount(post, it) }
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FirstComposeProjectTheme {
        HomeScreen(onCommentsClick = {})
    }
}
