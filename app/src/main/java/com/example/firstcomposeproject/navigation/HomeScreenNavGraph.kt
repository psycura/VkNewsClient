package com.example.firstcomposeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedPostId: Long) -> Unit,
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
                navArgument(Screen.KEY_FEED_POST_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val feedPostId = it.arguments?.getLong(Screen.KEY_FEED_POST_ID) ?: 0

            commentsScreenContent(feedPostId)
        }
    }

}