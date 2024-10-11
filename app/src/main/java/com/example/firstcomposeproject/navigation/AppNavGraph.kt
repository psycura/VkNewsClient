package com.example.firstcomposeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    favoriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedPostId: Long) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Route.HomeRoute
    ) {
        homeScreenNavGraph(
            newsFeedScreenContent = newsFeedScreenContent,
            commentsScreenContent = commentsScreenContent
        )

        composable<Route.FavouriteRoute> {
            favoriteScreenContent()
        }
        composable<Route.ProfileRoute> {
            profileScreenContent()
        }
    }
}