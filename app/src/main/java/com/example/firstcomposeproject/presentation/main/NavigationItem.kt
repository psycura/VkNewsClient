package com.example.firstcomposeproject.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.firstcomposeproject.R
import com.example.firstcomposeproject.navigation.Route

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector,
    val route: Route
) {
    data object Home: NavigationItem(
        titleResId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home,
        route = Route.HomeRoute
    )
    data object Favorite: NavigationItem(
        titleResId = R.string.navigation_item_favorite,
        icon = Icons.Outlined.Favorite,
        route = Route.FavouriteRoute,
    )
    data object Profile: NavigationItem(
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person,
        route = Route.ProfileRoute,
    )


}