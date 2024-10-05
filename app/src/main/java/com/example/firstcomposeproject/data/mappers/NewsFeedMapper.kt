package com.example.firstcomposeproject.data.mappers

import android.util.Log
import androidx.compose.ui.util.fastFirstOrNull
import com.example.firstcomposeproject.data.models.CommentsResponseDto
import com.example.firstcomposeproject.data.models.NewsFeedResponseDto
import com.example.firstcomposeproject.domain.FeedPost
import com.example.firstcomposeproject.domain.PostComment
import com.example.firstcomposeproject.domain.StatisticItem
import com.example.firstcomposeproject.domain.StatisticType
import kotlin.math.absoluteValue
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

class NewsFeedMapper {
    fun mapResponseToPosts(response: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = response.newsFeedContent.posts
        val groups = response.newsFeedContent.groups

        for (post in posts) {

            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break

            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, count = post.likes.count),
                    StatisticItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticItem(type = StatisticType.VIEWS, count = post.views.count),
                    StatisticItem(type = StatisticType.SHARES, count = post.reposts.count),
                ),
                isLiked = post.likes.userLikes > 0,
            )

            result.add(feedPost)
        }

        return result

    }

    fun mapResponseToComments(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()

        val comments = response.content.comments
        val profiles = response.content.profiles

        for (comment in comments) {
            if(comment.text.isBlank()) continue
            val author = profiles.fastFirstOrNull { it.id == comment.fromId } ?: break
            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstName} ${author.lastName}",
                authorAvatarUrl = author.avatarUrl,
                commentText = comment.text,
                publicationDate = mapTimestampToDate(comment.date),
            )

            result.add(postComment)
        }

        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val formatPattern = "d MMMM, yyyy, hh:mm"

        val date = Instant.fromEpochSeconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .toJavaLocalDateTime()

        return date.format(DateTimeFormatter.ofPattern(formatPattern))
    }
}