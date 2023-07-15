package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.felisreader.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoverField(coverUrl: String) {
    Surface(
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        GlideImage(
            model = coverUrl,
            contentDescription = "Manga cover",
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            it.placeholder(R.drawable.manga_cover)
        }
    }
}