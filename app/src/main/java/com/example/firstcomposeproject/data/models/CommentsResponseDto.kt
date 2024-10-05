package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponseDto(
    @SerialName("response") val content: CommentsContentDto,
)
