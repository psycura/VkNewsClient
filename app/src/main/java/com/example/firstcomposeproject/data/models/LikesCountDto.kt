package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikesCountDto(
    @SerialName("likes") val count: Int
)
