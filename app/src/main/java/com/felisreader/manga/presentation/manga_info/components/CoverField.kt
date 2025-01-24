package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.felisreader.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoverField(
    modifier: Modifier = Modifier,
    coverUrl: String
) {
    Surface(
        modifier = modifier,
        shadowElevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        GlideImage(
            model = coverUrl,
            contentDescription = "Manga cover",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        ) {
            it.placeholder(R.drawable.manga_cover)
        }
    }
}