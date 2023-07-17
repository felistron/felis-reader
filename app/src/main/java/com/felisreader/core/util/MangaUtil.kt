package com.felisreader.core.util

import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.Relationship
import com.felisreader.manga.data.source.remote.MangaService
import com.felisreader.manga.domain.model.Author
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.MangaEntity

class MangaUtil {
    companion object {
        fun mangaEntityToManga(mangaEntity: MangaEntity): Manga {
            // TODO: Return title and description based on user language preferences
            val title: String = mangaEntity.attributes.title[mangaEntity.attributes.title.keys.first()] ?: "No title"

            val originalLanguage: String = mangaEntity.attributes.originalLanguage
            var originalTitle: String = title

            mangaEntity.attributes.altTitles.forEach {
                if (it.containsKey(originalLanguage)) {
                    originalTitle = it[originalLanguage].toString()
                    return@forEach
                }
            }

            val description: String = mangaEntity.attributes.description["en"] ?: "No description"

            val coverRelationship: Relationship? = Relationship.queryRelationship(
                mangaEntity.relationships,
                EntityType.COVER_ART
            )

            // TODO
            val coverUrl: String = if (coverRelationship == null) "Unknown cover url"
            else "${MangaService.UPLOADS_BASE_URL}/covers/${mangaEntity.id}/${coverRelationship.attributes["fileName"].toString()}"

            val coverUrlSave: String = if (coverRelationship == null) "Unknown cover url"
            else "${MangaService.UPLOADS_BASE_URL}/covers/${mangaEntity.id}/${coverRelationship.attributes["fileName"].toString()}.512.jpg"


            val authorRelationship: Relationship? = Relationship.queryRelationship(mangaEntity.relationships, EntityType.AUTHOR)

            val authorName: String = if (authorRelationship == null) "Unknown author"
            else authorRelationship.attributes["name"].toString()

            return Manga(
                id = mangaEntity.id.toString(),
                title = title,
                originalTitle = originalTitle,
                description = description,
                coverUrl = coverUrl,
                coverUrlSave = coverUrlSave,
                author = Author(
                    name = authorName
                ),
                tags = mangaEntity.attributes.tags,
                contentRating = mangaEntity.attributes.contentRating,
                year = mangaEntity.attributes.year,
                status = mangaEntity.attributes.status
            )
        }
    }
}