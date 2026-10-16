package com.example.model

enum class ContentCategory(val labelHindi: String, val labelEnglish: String) {
    ALL("सभी", "All"),
    AARTI("आरती", "Aarti"),
    CHALISA("चालीसा", "Chalisa"),
    STOTRA("स्तोत्र", "Stotra"),
    MANTRA("मंत्र", "Mantra"),
    GEETA("गीता सार", "Gita Saar")
}

data class DevotionalItem(
    val id: String,
    val titleHindi: String,
    val titleEnglish: String,
    val deity: String,
    val category: ContentCategory,
    val summary: String,
    val lyricsHindi: String,
    val lyricsEnglish: String,
    val meaningHindi: String = "",
    val durationText: String = "5:20",
    val isFavorite: Boolean = false
)

data class PanchangDay(
    val gregorianDate: String,
    val dayOfWeekHindi: String,
    val vikramSamvat: String,
    val shakaSamvat: String,
    val monthNameHindi: String,
    val paksha: String,
    val tithi: String,
    val nakshatra: String,
    val yog: String,
    val karan: String,
    val sunRise: String,
    val sunSet: String,
    val moonRise: String,
    val rahuKaal: String,
    val abhijitMuhurat: String,
    val amritKaal: String,
    val vratOrFestival: String? = null,
    val choghadiyaDay: String = "शुभ (09:15 - 10:45)"
)

data class FestivalItem(
    val id: String,
    val titleHindi: String,
    val titleEnglish: String,
    val dateDisplay: String,
    val monthName: String,
    val dayName: String,
    val deity: String,
    val significance: String,
    val vidhi: String
)

data class SuvicharItem(
    val id: String,
    val sanskritVerse: String,
    val source: String,
    val hindiMeaning: String,
    val englishMeaning: String,
    val speaker: String
)

data class DeityInfo(
    val id: String,
    val nameHindi: String,
    val nameEnglish: String,
    val titleHindi: String,
    val mantra: String,
    val primaryColorHex: Long,
    val description: String
)
