package com.felisreader.manga.presentation.manga_search.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.felisreader.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoverThumbnail(coverUrl: String?, nsfw: Boolean = false) {
    GlideImage(
        model = coverUrl,
        contentDescription = "Manga cover thumbnail",
        modifier = Modifier
            .fillMaxHeight()
            .blur(if (nsfw) 5.dp else 0.dp)
            .aspectRatio(ratio = 2f / 3f)
    ) {
        it.placeholder(R.drawable.manga_cover)
            .centerCrop()
    }
}