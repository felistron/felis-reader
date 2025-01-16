package com.felisreader.core.presentation

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.felisreader.R
import com.felisreader.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableIntStateOf(1) }

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
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
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
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
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
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
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