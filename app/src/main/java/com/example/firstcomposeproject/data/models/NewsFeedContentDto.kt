package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsFeedContentDto(
    @SerialName("items") val posts: List<PostDto>,
    val groups: List<GroupDto>
)
