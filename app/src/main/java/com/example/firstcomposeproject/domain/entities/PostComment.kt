package com.example.firstcomposeproject.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationDate: String,
)
