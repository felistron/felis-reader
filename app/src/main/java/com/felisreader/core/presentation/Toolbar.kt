package com.felisreader.core.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.felisreader.R
import com.felisreader.Screen

@Composable
fun Toolbar(
    title: String = stringResource(id = R.string.app_name),
    navigation: NavHostController
) {
    SmallTopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.logo_foreground),
                contentDescription = "Logo",
                tint = Color.Unspecified,
                modifier = Modifier
                    .height(64.dp)
                    .padding(0.dp)
                    .clickable {
                        navigation.navigate(Screen.SearchScreen.route) {
                            launchSingleTop = true
                            popUpTo(Screen.SearchScreen.route) { inclusive = false }
                        }
                    }
            )
        },
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search outlined icon"
                )
            }
        }
    )
}
