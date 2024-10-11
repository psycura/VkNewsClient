package com.example.firstcomposeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedPostId: Long) -> Unit,
) {
    navigation<Route.HomeRoute>(
        startDestination = Route.NewsFeedRoute,
    ) {
        composable<Route.NewsFeedRoute>{
            newsFeedScreenContent()
        }
        composable<Route.CommentsRoute>{

            val feedPostId = it.toRoute<Route.CommentsRoute>().feedPostId

            commentsScreenContent(feedPostId)
        }
    }

}