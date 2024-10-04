package com.example.firstcomposeproject.navigation

import android.net.Uri
import com.example.firstcomposeproject.domain.FeedPost
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Screen(
    val route: String,
) {

    companion object {

        const val KEY_FEED_POST = "feed_post"

        const val ROUTE_HOME = "home"
        const val ROUTE_NEWS_FEED = "news"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }

    data object Home : Screen(ROUTE_HOME)
    data object NewsFeed : Screen(ROUTE_NEWS_FEED)
    data object Favourite : Screen(ROUTE_FAVOURITE)
    data object Profile : Screen(ROUTE_PROFILE)

    data object Comments : Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String {
            val feedPostString = Json.encodeToString(feedPost)

            return "$ROUTE_FOR_ARGS/${feedPostString}"
        }
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}

fun String.decode(): String {
    return Uri.decode(this)
}

