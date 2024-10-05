package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: Long,
    @SerialName("from_id") val fromId: Long,
    val date: Long,
    val text: String,
)
