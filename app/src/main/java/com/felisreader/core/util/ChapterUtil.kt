package com.felisreader.core.util

import com.felisreader.R
import com.felisreader.chapter.domain.model.api.Chapter
import com.felisreader.core.domain.model.api.EntityType

object ChapterUtil {
    fun countryFlagFromLangCode(code: String): Int {
        return when (code) {
            "ja" -> R.drawable.jp
            "ko" -> R.drawable.kr
            "zh" -> R.drawable.cn
            "zh-hk" -> R.drawable.hk
            "en" -> R.drawable.gb
            "ar" -> R.drawable.sa
            "az" -> R.drawable.az
            "bg" -> R.drawable.bd
            "bn" -> R.drawable.bg
            "ca" -> R.drawable.mm
            "my" -> R.drawable.ad
            "hr" -> R.drawable.hr
            "cs" -> R.drawable.cz
            "da" -> R.drawable.dk
            "nl" -> R.drawable.nl
            "et" -> R.drawable.et
            "tl" -> R.drawable.ph
            "fi" -> R.drawable.fi
            "fr" -> R.drawable.fr
            "de" -> R.drawable.de
            "el" -> R.drawable.gr
            "he" -> R.drawable.il
            "hu" -> R.drawable.hu
            "id" -> R.drawable.id
            "it" -> R.drawable.it
            "kk" -> R.drawable.kz
            "lt" -> R.drawable.lt
            "ms" -> R.drawable.my
            "mn" -> R.drawable.mn
            "ne" -> R.drawable.np
            "no" -> R.drawable.no
            "fa" -> R.drawable.ir
            "pl" -> R.drawable.pl
            "pt" -> R.drawable.pt
            "pt-br" -> R.drawable.br
            "ro" -> R.drawable.ro
            "ru" -> R.drawable.ru
            "sr" -> R.drawable.rs
            "sk" -> R.drawable.sk
            "es" -> R.drawable.es
            "es-la" -> R.drawable.mx
            "sv" -> R.drawable.se
            "th" -> R.drawable.th
            "tr" -> R.drawable.tr
            "uk" -> R.drawable.ua
            "vi" -> R.drawable.vn
            else -> R.drawable.xx
        }
    }

    // TODO: Implement ScanlationGroup entity from API
    fun getScanlationGroups(chapter: Chapter): List<String> {
        val groups: MutableList<String> = mutableListOf()
        chapter.relationships.forEach {
            if (it.type == EntityType.SCANLATION_GROUP) groups.add(it.attributes["name"] as String)
        }
        return groups
    }

    // TODO: Implement ScanlationUser entity from API
    fun getUploaders(chapter: Chapter): List<String> {
        val uploaders: MutableList<String> = mutableListOf()
        chapter.relationships.forEach {
            if (it.type == EntityType.USER) uploaders.add(it.attributes["username"] as String)
        }
        return uploaders
    }

    fun List<Chapter>.groupByVolumeAndChapter(): Map<String, Map<String, List<Chapter>>> {
        val groupedVolume: Map<String, List<Chapter>> = this.groupBy { it.attributes.volume ?: "N/A" }

        return groupedVolume.entries.associate { volume ->
            volume.key to volume.value.groupBy { chapter ->
                chapter.attributes.chapter ?: "N/A"
            }
        }
    }
}