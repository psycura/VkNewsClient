package com.example.firstcomposeproject.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AttachmentDto(
    val photo: PhotoDto? = null
)
