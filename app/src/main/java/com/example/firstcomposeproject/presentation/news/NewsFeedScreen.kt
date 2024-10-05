package com.example.firstcomposeproject.presentation.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.ui.theme.DarkBLue
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
            onCommentsClick = onCommentsClick,
            nextDataIsLoading = state.nextDataIsLoading
        )

        NewsFeedScreenState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = DarkBLue)
        }

        NewsFeedScreenState.Initial -> {}
    }


}

@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    modifier: Modifier = Modifier,
    vm: NewsFeedViewModel,
    onCommentsClick: (FeedPost) -> Unit,
    nextDataIsLoading: Boolean,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts, key = { it.id }) { post ->
            val swipeState = rememberSwipeToDismissBoxState()

            if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart && swipeState.progress >= .5f) {
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
                    onCommentsClick = { onCommentsClick(post) },
                    onLikeClick = { vm.changeLikeStatus(post) }
                )
            }
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBLue)
                }
            } else {
                SideEffect {
                    vm.loadNextPosts()
                }
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
