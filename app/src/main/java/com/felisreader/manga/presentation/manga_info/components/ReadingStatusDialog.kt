package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.felisreader.R
import com.felisreader.user.domain.model.api.ReadingStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingStatusDialog(
    defaultOption: ReadingStatus?,
    onDismiss: () -> Unit,
    onConfirm: (status: ReadingStatus?) -> Unit,
) {
    val options = listOf(
        Pair(null, stringResource(id = R.string.none)),
        Pair(ReadingStatus.READING, stringResource(id = R.string.reading_status_reading)),
        Pair(ReadingStatus.ON_HOLD, stringResource(id = R.string.reading_status_on_hold)),
        Pair(ReadingStatus.PLAN_TO_READ, stringResource(id = R.string.reading_status_plan_to_read)),
        Pair(ReadingStatus.DROPPED, stringResource(id = R.string.reading_status_dropped)),
        Pair(ReadingStatus.RE_READING, stringResource(id = R.string.reading_status_re_reading)),
        Pair(ReadingStatus.COMPLETED, stringResource(id = R.string.reading_status_completed)),
    )

    var selectedOption by remember { mutableIntStateOf(options.indexOfFirst { it.first == defaultOption }) }

    AlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            shadowElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.reading_status),
                    style = MaterialTheme.typography.titleLarge,
                )
                options.forEachIndexed { index, option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (index == selectedOption),
                                onClick = { selectedOption = index },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = (index == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = option.second,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        ),
                        onClick = onDismiss
                    ) {
                        Text(stringResource(id = R.string.ui_cancel))
                    }
                    Button(
                        enabled = defaultOption != options.getOrNull(selectedOption)?.first,
                        onClick = {
                            options.getOrNull(selectedOption)?.let { option ->
                                onConfirm(option.first)
                            }
                        }
                    ) {
                        Text(stringResource(id = R.string.ui_add))
                    }
                }
            }
        }
    }
}