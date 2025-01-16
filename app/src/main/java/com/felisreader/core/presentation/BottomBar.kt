package com.felisreader.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.felisreader.R
import com.felisreader.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showNavigationBar = currentRoute != Screen.LectorScreen().route

    AnimatedVisibility(showNavigationBar) {
        NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            tonalElevation = 0.dp,
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(stringResource(id = R.string.ui_home))
                },
                selected = currentRoute == Screen.HomeScreen().route,
                onClick = {
                    navController.navigate(Screen.HomeScreen().route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(stringResource(id = R.string.ui_search))
                },
                selected = currentRoute == Screen.SearchScreen().route,
                onClick = {
                    navController.navigate(Screen.SearchScreen().route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.LibraryBooks,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(stringResource(id = R.string.ui_library))
                },
                selected = currentRoute == Screen.LibraryScreen().route,
                onClick = {
                    navController.navigate(Screen.LibraryScreen().route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}