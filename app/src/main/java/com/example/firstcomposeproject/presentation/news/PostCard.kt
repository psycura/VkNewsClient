package com.example.firstcomposeproject.presentation.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.StatisticItem

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    onLikeClick: (StatisticItem) -> Unit,
    onCommentsClick: (StatisticItem) -> Unit,
    post: FeedPost,
) {

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader(post)
            Spacer(modifier = Modifier.height(8.dp))
            Text(post.contentText)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = post.contentImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                post.statistics,
                onLikeClick = { onLikeClick(it) },
                onCommentsClick = { onCommentsClick(it) },
                isLiked = post.isLiked
            )
        }

    }
}

