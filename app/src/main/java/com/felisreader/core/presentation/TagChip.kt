package com.felisreader.core.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felisreader.core.domain.model.ContentRating
import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.PublicationDemographic
import com.felisreader.manga.domain.model.TagAttributes
import com.felisreader.manga.domain.model.TagEntity
import com.felisreader.manga.domain.model.TagGroup
import java.util.*

@Composable
fun TagChip(
    tagName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    nsfw: Boolean = false,

) {
    AssistChip(
        modifier = modifier,
        onClick = onClick,
        label = {
            Text(
                text = tagName,
                fontSize = 14.sp,
                modifier = Modifier.padding(0.dp)
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (nsfw) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surface,
            labelColor = if (nsfw) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface
        ),
        leadingIcon = null,
        trailingIcon = null,
        shape = MaterialTheme.shapes.large,
        border = if (nsfw) null else AssistChipDefaults.assistChipBorder()
    )
}

@Preview(showBackground = true)
@Composable
private fun TagChipPreview() {
    TagChip(
        tagName = "Comedy",
        onClick = { },
        modifier = Modifier.padding(horizontal = 0.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagChipGroup(
    modifier: Modifier = Modifier,
    tags: List<TagEntity>,
    onTagClick: (tagId: String) -> Unit,
    contentRating: ContentRating? = null,
    onContentRatingClick: (contentRating: String) -> Unit = { },
    demography: PublicationDemographic? = null,
    onDemographyClick: (demography: String) -> Unit = { }
) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        if (contentRating != null) {
            TagChip(
                tagName = contentRating.value,
                onClick = { onContentRatingClick(contentRating.name.lowercase()) },
                nsfw = true,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp).height(25.dp)
            )
        }
        if (demography != null) {
            TagChip(
                tagName = demography.value,
                onClick = { onDemographyClick(demography.name.lowercase()) },
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp).height(25.dp)
            )
        }
        tags.forEach {
            TagChip(
                tagName = it.attributes.name["en"].toString(),
                onClick = { onTagClick(it.id.toString()) },
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp).height(25.dp),
                nsfw = it.attributes.group == TagGroup.CONTENT
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TagChipGroupPreview() {
    val tags: List<TagEntity> = listOf(
        TagEntity(
            id = UUID.randomUUID(),
            type = EntityType.TAG,
            attributes = TagAttributes(
                name = mapOf("en" to "Comedy"),
                description = mapOf("en" to "Comedy"),
                group = TagGroup.GENRE,
                version = 1
            ),
            relationships = emptyList()
        ),
        TagEntity(
            id = UUID.randomUUID(),
            type = EntityType.TAG,
            attributes = TagAttributes(
                name = mapOf("en" to "Romance"),
                description = mapOf("en" to "Romance"),
                group = TagGroup.GENRE,
                version = 1
            ),
            relationships = emptyList()
        ),
        TagEntity(
            id = UUID.randomUUID(),
            type = EntityType.TAG,
            attributes = TagAttributes(
                name = mapOf("en" to "Action"),
                description = mapOf("en" to "Action"),
                group = TagGroup.GENRE,
                version = 1
            ),
            relationships = emptyList()
        )
    )

    TagChipGroup(
        tags = tags,
        onTagClick = { }
    )
}