package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SpiritualSoundManager
import com.example.data.DevotionalRepository
import com.example.data.SamitiDetails
import com.example.model.ContentCategory
import com.example.model.DeityInfo
import com.example.model.DevotionalItem
import com.example.model.FestivalItem
import com.example.model.PanchangDay
import com.example.model.SuvicharItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

enum class ScreenDestination(val titleHindi: String, val titleEnglish: String) {
    HOME("दैनिक", "Daily"),
    SANGRAH("संग्रह", "Sangrah"),
    MANDIR("पूजा घर", "Mandir"),
    JAPA("जाप माला", "Japa"),
    PANCHANG("पंचांग", "Panchang"),
    ABOUT("समिति", "Samiti")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val soundManager = SpiritualSoundManager(application)
    private val repository = DevotionalRepository()

    // Navigation
    private val _currentScreen = MutableStateFlow(ScreenDestination.HOME)
    val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

    fun navigateTo(screen: ScreenDestination) {
        _currentScreen.value = screen
    }

    // Devotional library & search/filter
    private val _selectedCategory = MutableStateFlow(ContentCategory.ALL)
    val selectedCategory: StateFlow<ContentCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val favoriteIds: StateFlow<Set<String>> = repository.favoriteIds

    val devotionalItems: StateFlow<List<DevotionalItem>> = combine(
        _selectedCategory,
        _searchQuery,
        repository.favoriteIds
    ) { category, query, favorites ->
        val all = repository.getAllDevotionalItems().map {
            it.copy(isFavorite = favorites.contains(it.id))
        }
        all.filter { item ->
            val matchCategory = if (category == ContentCategory.ALL) true else item.category == category
            val matchQuery = if (query.isBlank()) true else {
                item.titleHindi.contains(query, ignoreCase = true) ||
                item.titleEnglish.contains(query, ignoreCase = true) ||
                item.deity.contains(query, ignoreCase = true)
            }
            matchCategory && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Currently opened item in reader
    private val _activeReaderItem = MutableStateFlow<DevotionalItem?>(null)
    val activeReaderItem: StateFlow<DevotionalItem?> = _activeReaderItem.asStateFlow()

    // Reader font scale factor (0: normal, 1: medium, 2: large)
    private val _readerFontSizeStep = MutableStateFlow(1)
    val readerFontSizeStep: StateFlow<Int> = _readerFontSizeStep.asStateFlow()

    fun setReaderFontSizeStep(step: Int) {
        _readerFontSizeStep.value = step.coerceIn(0, 2)
    }

    fun openReader(item: DevotionalItem) {
        _activeReaderItem.value = item
    }

    fun closeReader() {
        _activeReaderItem.value = null
    }

    fun toggleFavorite(id: String) {
        repository.toggleFavorite(id)
    }

    fun setCategory(category: ContentCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Mandir State
    val deities: List<DeityInfo> = repository.getDeities()
    private val _selectedDeity = MutableStateFlow(deities.first())
    val selectedDeity: StateFlow<DeityInfo> = _selectedDeity.asStateFlow()

    private val _isDiyaLit = MutableStateFlow(true)
    val isDiyaLit: StateFlow<Boolean> = _isDiyaLit.asStateFlow()

    private val _isDhoopLit = MutableStateFlow(true)
    val isDhoopLit: StateFlow<Boolean> = _isDhoopLit.asStateFlow()

    private val _flowerShowerTrigger = MutableStateFlow(0)
    val flowerShowerTrigger: StateFlow<Int> = _flowerShowerTrigger.asStateFlow()

    private val _isAartiRotating = MutableStateFlow(false)
    val isAartiRotating: StateFlow<Boolean> = _isAartiRotating.asStateFlow()

    fun selectDeity(deity: DeityInfo) {
        _selectedDeity.value = deity
        soundManager.playTempleBell()
    }

    fun toggleDiya() {
        _isDiyaLit.value = !_isDiyaLit.value
        soundManager.vibrateBead()
    }

    fun toggleDhoop() {
        _isDhoopLit.value = !_isDhoopLit.value
        soundManager.vibrateBead()
    }

    fun offerFlowers() {
        _flowerShowerTrigger.value += 1
        soundManager.playTempleBell()
    }

    fun toggleAartiThali() {
        _isAartiRotating.value = !_isAartiRotating.value
        if (_isAartiRotating.value) {
            soundManager.startAartiRhythm()
        } else {
            soundManager.stopAartiRhythm()
        }
    }

    // Japa Mala State
    val availableMantras = listOf(
        "श्री राम जय राम जय जय राम",
        "ॐ नमः शिवाय",
        "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे। हरे राम हरे राम राम राम हरे हरे",
        "ॐ हं हनुमते नमः",
        "ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं भर्गो देवस्य धीमहि धियो यो नः प्रचोदयात्",
        "ॐ गं गणपतये नमः",
        "ॐ नमो भगवते वासुदेवाय"
    )

    private val _currentMantra = MutableStateFlow(availableMantras.first())
    val currentMantra: StateFlow<String> = _currentMantra.asStateFlow()

    private val _malaBeadCount = MutableStateFlow(0)
    val malaBeadCount: StateFlow<Int> = _malaBeadCount.asStateFlow()

    private val _totalMalasCompleted = MutableStateFlow(0)
    val totalMalasCompleted: StateFlow<Int> = _totalMalasCompleted.asStateFlow()

    private val _targetMalas = MutableStateFlow(1)
    val targetMalas: StateFlow<Int> = _targetMalas.asStateFlow()

    fun selectMantra(mantra: String) {
        _currentMantra.value = mantra
    }

    fun setTargetMalas(target: Int) {
        _targetMalas.value = target.coerceAtLeast(1)
    }

    fun incrementBead() {
        soundManager.playMalaClick()
        val nextCount = _malaBeadCount.value + 1
        if (nextCount >= 108) {
            _malaBeadCount.value = 0
            _totalMalasCompleted.value += 1
            soundManager.vibrateCelebration()
            soundManager.playTempleBell()
        } else {
            _malaBeadCount.value = nextCount
        }
    }

    fun resetJapa() {
        _malaBeadCount.value = 0
        soundManager.vibrateBead()
    }

    // Daily Panchang, Suvichar, Festivals
    val todayPanchang: PanchangDay = repository.getTodayPanchang()
    val suvicharList: List<SuvicharItem> = repository.getSuvicharList()
    val festivals: List<FestivalItem> = repository.getUpcomingFestivals()
    val samitiInfo: SamitiDetails = repository.getSanatanSevaSamitiInfo()

    override fun onCleared() {
        super.onCleared()
        soundManager.stopOmDrone()
        soundManager.stopAartiRhythm()
    }
}
