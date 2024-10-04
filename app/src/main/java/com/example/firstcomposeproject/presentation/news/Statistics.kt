package com.example.firstcomposeproject.presentation.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.firstcomposeproject.R
import com.example.firstcomposeproject.domain.StatisticItem
import com.example.firstcomposeproject.domain.StatisticType

@Composable
fun Statistics(
    statistics: List<StatisticItem>,
    onLikeClick: (StatisticItem) -> Unit,
    onShareClick: (StatisticItem) -> Unit,
    onViewsClick: (StatisticItem) -> Unit,
    onCommentsClick: (StatisticItem) -> Unit,
) {
    Row {
        Row(
            modifier = Modifier.weight(1f),
        ) {
            val countItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = countItem.count.toString(),
                onItemClickListener = { onViewsClick(countItem) }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                text = sharesItem.count.toString(),
                onItemClickListener = { onShareClick(sharesItem) }
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = commentsItem.count.toString(),
                onItemClickListener = { onCommentsClick(commentsItem) }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = R.drawable.ic_like,
                text = likesItem.count.toString(),
                onItemClickListener = { onLikeClick(likesItem) }
            )
        }

    }
}


private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

