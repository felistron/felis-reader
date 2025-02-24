package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.felisreader.R
import com.felisreader.manga.domain.model.api.StatisticsRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingDialog(
    visible: Boolean,
    rating: StatisticsRating,
    onDismiss: () -> Unit,
    loggedIn: Boolean,
    onSignInClick: () -> Unit,
    selectedRating: Int,
    onRatingSelected: (rating: Int) -> Unit,
) {
    val total = rating.distribution?.values?.sum() ?: 1

    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = MaterialTheme.shapes.medium,
            ) {
                if (rating.distribution != null) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    ) {
                        items(
                            items = rating.distribution.toList().reversed(),
                            key = { it.first }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = it.first
                                )
                                LinearProgressIndicator(
                                    modifier = Modifier.height(10.dp),
                                    progress = it.second.toFloat() / total,
                                    strokeCap = StrokeCap.Round
                                )
                            }
                        }

                        item {
                            Divider(modifier = Modifier.padding(vertical = 16.dp))
                        }

                        item {
                            if (loggedIn) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.rate)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        for (i in 1..10) {
                                            IconButton(
                                                modifier = Modifier.size(28.dp),
                                                onClick = {
                                                    onRatingSelected(i)
                                                },
                                            ) {
                                                Icon(
                                                    imageVector = if (i <= selectedRating) Icons.Rounded.Star else Icons.Rounded.StarBorder,
                                                    contentDescription = null,
                                                    tint = if (i <= selectedRating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFE6613E),
                                            contentColor = Color.White,
                                        ),
                                        onClick = onSignInClick,
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Text(stringResource(id = R.string.ui_signin_mangadex))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}