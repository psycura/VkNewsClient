package com.example.firstcomposeproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val id: Long,
    val name: String,
    @SerialName("photo_200") val imageUrl: String,
)
