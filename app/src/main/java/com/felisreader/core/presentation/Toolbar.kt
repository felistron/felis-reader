package com.felisreader.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.felisreader.R
import com.felisreader.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String = stringResource(id = R.string.app_name),
    navigation: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val text: MutableState<String> = remember { mutableStateOf("") }
    val searchBarActive: MutableState<Boolean> = remember { mutableStateOf(false) }
    val focusRequester: FocusRequester = remember { FocusRequester() }

    TopAppBar(
        navigationIcon = {
            AnimatedVisibility(!searchBarActive.value) {
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
            }
        },
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            AnimatedVisibility(!searchBarActive.value) {
                IconButton(onClick = {
                    searchBarActive.value = true
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search outlined icon"
                    )
                }
            }
            AnimatedVisibility(searchBarActive.value) {
                LaunchedEffect(true) {
                    focusRequester.requestFocus()
                }
                OutlinedTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    value = text.value,
                    onValueChange = { text.value = it },
                    singleLine = true,
                    placeholder = {
                        Text(text = "Search by title")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (text.value.isEmpty()) {
                                searchBarActive.value = false
                            } else {
                                text.value = ""
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon"
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.large,
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            // TODO: Search action
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    )
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}
