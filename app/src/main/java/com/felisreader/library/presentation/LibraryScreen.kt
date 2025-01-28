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
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.library.presentation.components.MangadexSignIn

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel(),
    navigateToMangaHistory: () -> Unit,
) {
    AnimatedVisibility(viewModel.state.value.signInDialogVisible) {
        MangadexSignIn(setVisible = { viewModel.onEvent(LibraryEvent.SignInDialogVisible(it)) })
    }

    LibraryContent(
        navigateToMangaHistory = navigateToMangaHistory,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun LibraryContent(
    navigateToMangaHistory: () -> Unit,
    onEvent: (LibraryEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = { onEvent(LibraryEvent.SignInDialogVisible(true)) }
            ) {
                Text(stringResource(id = R.string.ui_signin_mangadex))
            }
        }
    }
}

@Composable
fun LibraryItem(
    name: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
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