package com.felisreader.manga.domain.model.api

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.api.IncludedMode

data class MangaListQuery(
    val limit: Int? = null,
    val offset: Int? = null,
    val title: String? = null,
    val authorOrArtist: String? = null,
    val authors: List<String>? = null,
    val artist: List<String>? = null,
    val year: Int? = null,
    val includedTags: List<String>? = null,
    val includedTagsMode: IncludedMode? = null,
    val excludedTags: List<String>? = null,
    val excludedTagsMode: IncludedMode? = null,
    val status: List<Status>? = null,
    val originalLanguage: List<String>? = null,
    val excludedOriginalLanguage: List<String>? = null,
    val availableTranslatedLanguage: List<String>? = null,
    val publicationDemographic: List<PublicationDemographic>? = null,
    val ids: List<String>? = null,
    val contentRating: List<ContentRating>? = null,
    //val createdAtSince: LocalDate? = null,
    //val updatedAtSince: LocalDate? = null,
    val order: List<MangaOrder>? = null,
    val includes: List<EntityType>? = null,
    val hasAvailableChapters: Boolean? = null,
    val group: String? = null
) {
//    companion object {
//        @RequiresApi(Build.VERSION_CODES.O)
//        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss")
//    }
}
