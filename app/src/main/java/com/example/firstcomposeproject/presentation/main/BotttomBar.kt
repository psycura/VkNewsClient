package com.example.firstcomposeproject.presentation.main

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.firstcomposeproject.navigation.NavigationState

@Composable
fun BottomBar(
    navigationState: NavigationState,
) {
    NavigationBar {

        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favorite,
            NavigationItem.Profile
        )

        items.forEach { item ->
            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                Log.d("CURRENT_NAV_DESTINATION","$it")

                it.route == item.screen.route
            } ?: false

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navigationState.navigateTo(item.screen.route)
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(text = stringResource(item.titleResId)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                )
            )
        }
    }
}