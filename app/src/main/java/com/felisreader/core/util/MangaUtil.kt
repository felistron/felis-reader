package com.felisreader.core.util

import android.util.Log
import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.LinkType
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

            Log.i("LINKS_TEST", "${mangaEntity.attributes.links}")

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
                status = mangaEntity.attributes.status,
                links = if (mangaEntity.attributes.links == null) emptyList() else mangaEntity.attributes.links.entries.map {
                    when (it.key) {
                        "al" -> LinkType.AL(it.value)
                        "ap" -> LinkType.AP(it.value)
                        "bw" -> LinkType.BW(it.value)
                        "mu" -> LinkType.MU(it.value)
                        "nu" -> LinkType.NU(it.value)
                        //"kt" -> LinkType.KT(it.value) TODO
                        "amz" -> LinkType.AMZ(it.value)
                        "ebj" -> LinkType.EBJ(it.value)
                        "mal" -> LinkType.MAL(it.value)
                        "cdj" -> LinkType.CDJ(it.value)
                        "engtl" -> LinkType.ENGTL(it.value)
                        "raw" -> LinkType.RAW(it.value)
                        else -> null
                    }
                }
            )
        }
    }
}