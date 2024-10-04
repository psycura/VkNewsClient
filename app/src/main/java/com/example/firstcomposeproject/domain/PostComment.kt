package com.example.firstcomposeproject.domain

import com.example.firstcomposeproject.R
import kotlinx.serialization.Serializable

@Serializable
data class PostComment(
    val id: Int = 0,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.author_avatar,
    val commentText: String = "Suspendisse semper ante nec eros pretium, at tempor erat volutpat.",
    val publicationDate: String = "14:00"
)
