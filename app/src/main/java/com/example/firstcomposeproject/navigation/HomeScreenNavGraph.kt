package com.example.firstcomposeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.firstcomposeproject.domain.FeedPost
import kotlinx.serialization.json.Json


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit,
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(
            route = Screen.NewsFeed.route
        ) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = NavType.StringType
                }
            )
        ) {
            val feedPostString = it.arguments?.getString(Screen.KEY_FEED_POST) ?: ""
            val feedPost = Json.decodeFromString<FeedPost>(feedPostString)

            commentsScreenContent(feedPost)
        }
    }

}