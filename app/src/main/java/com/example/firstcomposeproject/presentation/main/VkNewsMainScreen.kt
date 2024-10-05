package com.example.firstcomposeproject.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstcomposeproject.navigation.AppNavGraph
import com.example.firstcomposeproject.navigation.Screen
import com.example.firstcomposeproject.navigation.rememberNavigationState
import com.example.firstcomposeproject.presentation.comments.CommentsScreen
import com.example.firstcomposeproject.presentation.news.HomeScreen
import com.example.firstcomposeproject.ui.theme.FirstComposeProjectTheme

@Composable
fun VkNewsMainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = { BottomBar(navigationState) },
    )
    { contentPadding ->
        AppNavGraph(
            navController = navigationState.navHostController,
            newsFeedScreenContent = {
                HomeScreen(
                    modifier = Modifier.padding(contentPadding),
                    onCommentsClick = {
                        navigationState.navigateTo(Screen.Comments.getRouteWithArgs(it))
                    }
                )
            },
            commentsScreenContent = {
                CommentsScreen(
                    onBackPress = { navigationState.navHostController.popBackStack() },
                    feedPost = it,
                )
            },
            favoriteScreenContent = {
                TextWithCounter(
                    text = "Favorite",
                    modifier = Modifier.padding(contentPadding)
                )
            },
            profileScreenContent = {
                TextWithCounter(
                    text = "Profile",
                    modifier = Modifier.padding(contentPadding)
                )
            },

            )
    }
}

@Composable
fun TextWithCounter(modifier: Modifier = Modifier, text: String) {
    var counterState by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text("$text $counterState",
            modifier = Modifier
                .clickable {
                    counterState++
                }
                .background(Color.Cyan)
                .padding(16.dp)

        )
    }
}

@Preview(showBackground = true)
@Composable
fun VkNewsMainScreenPreviewLight() {
    FirstComposeProjectTheme(darkTheme = false) {
        VkNewsMainScreen()
    }
}


@Preview(showBackground = true)
@Composable
fun VkNewsMainScreenPreviewDark() {
    FirstComposeProjectTheme(darkTheme = true) {
        VkNewsMainScreen()
    }
}