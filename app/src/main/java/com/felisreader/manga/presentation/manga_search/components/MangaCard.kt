package com.felisreader.manga.presentation.manga_search.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felisreader.core.domain.model.ContentRating
import com.felisreader.core.presentation.TagChipGroup
import com.felisreader.manga.domain.model.Manga

@Composable
fun MangaCard(
    manga: Manga,
    onCardClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        onClick = onCardClick
    ) {
        Row {
            CoverThumbnail(coverUrl = manga.coverUrlSave)
            Column {
                Text(
                    text = manga.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                TagChipGroup(
                    tags = manga.tags.subList(0, if (manga.tags.size >= 2) 2 else manga.tags.size),
                    onTagClick = {},
                    contentRating = if (manga.contentRating != ContentRating.SAFE) manga.contentRating else null,
                    onContentRatingClick = {},
                    demography = manga.demography,
                    onDemographyClick = {}
                )
                Text(
                    text = manga.description,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 6,
                    lineHeight = 17.sp
                )
            }
        }
    }
}