package com.example.firstcomposeproject.presentation.news

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.firstcomposeproject.R
import com.example.firstcomposeproject.domain.StatisticItem
import com.example.firstcomposeproject.domain.StatisticType
import com.example.firstcomposeproject.ui.theme.DarkRed

@Composable
fun Statistics(
    statistics: List<StatisticItem>,
    onLikeClick: (StatisticItem) -> Unit,
    onCommentsClick: (StatisticItem) -> Unit,
    isLiked: Boolean,
) {
    Row {
        Row(
            modifier = Modifier.weight(1f),
        ) {
            val countItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = formatStatisticCount(countItem.count),
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                text = formatStatisticCount(sharesItem.count),
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticCount(commentsItem.count),
                onItemClickListener = { onCommentsClick(commentsItem) }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = if (!isLiked) R.drawable.ic_like else R.drawable.ic_like_set,
                tint = if (!isLiked) MaterialTheme.colorScheme.onSecondary else DarkRed,
                text = formatStatisticCount(likesItem.count),
                onItemClickListener = { onLikeClick(likesItem) }
            )
        }

    }
}

@SuppressLint("DefaultLocale")
private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", count / 1000)
    } else if (count > 1000) {
        String.format("%.1fK", count / 1000f)
    } else {
        count.toString()
    }

}


private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

