package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentsContentDto(
    @SerialName("items") val comments: List<CommentDto>,
    val profiles: List<ProfileDto>,
)
