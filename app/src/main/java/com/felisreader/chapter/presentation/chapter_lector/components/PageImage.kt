package com.felisreader.chapter.presentation.chapter_lector.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.felisreader.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PageImage(
    imageUrl: String
) {
    // TODO: Add report
    GlideImage(
        modifier = Modifier.fillMaxWidth(),
        model = imageUrl,
        contentDescription = null,
    ) {
        it.placeholder(R.drawable.manga_cover)
    }
}
