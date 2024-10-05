package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IgnoreItemResponseDto (
    @SerialName("response") val ignoreItem: IgnoreItemDto
)