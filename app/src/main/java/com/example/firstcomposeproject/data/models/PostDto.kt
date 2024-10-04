package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    @SerialName("post_id")val id: String,

    @SerialName("is_favorite") val isFavorite: Boolean,
    @SerialName("source_id") val communityId: Long,

    val text: String,
    val date: Long,

    val likes: LikesDto,
    val comments:  CommentsDto,
    val views:  ViewsDto,
    val reposts:  RepostsDto,
    val attachments: List<AttachmentDto>,

)
