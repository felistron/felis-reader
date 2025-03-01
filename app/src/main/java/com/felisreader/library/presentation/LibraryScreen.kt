package com.felisreader.library.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.user.presentation.signin.SignInDialog

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel(),
    navigateToMangaHistory: () -> Unit,
    navigateToReadingHistory: () -> Unit,
    navigateToFollows: () -> Unit,
) {
    AnimatedVisibility(viewModel.state.value.signInDialogVisible) {
        SignInDialog(
            onSuccess = {
                viewModel.onEvent(LibraryEvent.SignInDialogVisible(false))
                viewModel.onEvent(LibraryEvent.SignInSuccess)
            },
            onCancel = {
                viewModel.onEvent(LibraryEvent.SignInDialogVisible(false))
           },
        )
    }

    LibraryContent(
        navigateToMangaHistory = navigateToMangaHistory,
        onEvent = viewModel::onEvent,
        isLoggedIn = viewModel.state.value.isLoggedIn,
        navigateToReadingHistory = navigateToReadingHistory,
        navigateToFollows = navigateToFollows,
    )
}

@Composable
fun LibraryContent(
    isLoggedIn: Boolean,
    navigateToMangaHistory: () -> Unit,
    navigateToReadingHistory: () -> Unit,
    navigateToFollows: () -> Unit,
    onEvent: (LibraryEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.reading_lists),
                style = MaterialTheme.typography.titleLarge
            )
        }
        LibraryItem(
            name = stringResource(id = R.string.ui_library_manga_history),
            icon = {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            },
            onClick = navigateToMangaHistory
        )
        LibraryItem(
            name = stringResource(id = R.string.library_reading_history),
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            },
            onClick = navigateToReadingHistory
        )
        Divider()
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.md_lists),
                style = MaterialTheme.typography.titleLarge
            )
        }
        if (!isLoggedIn) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6613E),
                        contentColor = Color.White,
                    ),
                    onClick = {
                        onEvent(LibraryEvent.SignInDialogVisible(true))
                    }
                ) {
                    Text(stringResource(id = R.string.ui_signin_mangadex))
                }
            }
        } else {
            LibraryItem(
                name = stringResource(id = R.string.ui_library),
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.LibraryBooks,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                },
                onClick = navigateToFollows
            )
        }
    }
}

@Composable
fun LibraryItem(
    name: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
            )
            icon()
        }
    }
}