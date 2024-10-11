package com.example.firstcomposeproject.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object HomeRoute : Route()

    @Serializable
    data object NewsFeedRoute : Route()

    @Serializable
    data object FavouriteRoute : Route()

    @Serializable
    data object ProfileRoute : Route()

    @Serializable
    data class CommentsRoute(
        val feedPostId: Long,
    ) : Route()
}


