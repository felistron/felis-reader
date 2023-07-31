package com.felisreader.chapter.presentation.chapter_lector.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.felisreader.chapter.domain.model.AggregateChapter

@Composable
fun NavigationField(
    prevChapter: AggregateChapter?,
    nextChapter: AggregateChapter?,
    navigateToChapter: (chapterId: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (prevChapter != null) {
            Button(
                onClick = { navigateToChapter(prevChapter.id) },
                colors = ButtonDefaults.textButtonColors()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous chapter"
                )
                Text(text = "Ch. ${prevChapter.chapter}")
            }
        }

        Spacer(modifier = Modifier.height(1.dp))

        if (nextChapter != null) {
            Button(
                onClick = { navigateToChapter(nextChapter.id) },
                colors = ButtonDefaults.textButtonColors()
            ) {
                Text(text = "Ch. ${nextChapter.chapter}")
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next chapter"
                )
            }
        }
    }
}