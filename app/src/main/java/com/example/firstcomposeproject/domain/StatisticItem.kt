package com.example.firstcomposeproject.domain

import kotlinx.serialization.Serializable

enum class StatisticType {
    VIEWS,
    SHARES,
    COMMENTS,
    LIKES
}


@Serializable
data class StatisticItem(
    val type: StatisticType,
    val count: Int = 0
)
