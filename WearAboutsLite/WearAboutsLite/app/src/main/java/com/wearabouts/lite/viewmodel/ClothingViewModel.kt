package com.wearabouts.lite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.data.local.UserPreferences
import com.wearabouts.lite.data.model.ClothingItem
import com.wearabouts.lite.data.model.HistoryActivity
import com.wearabouts.lite.data.repository.ClothingRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClothingViewModel(
    private val repository: ClothingRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val allItems: StateFlow<List<ClothingItem>> = repository.allItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val history: StateFlow<List<HistoryActivity>> = repository.allHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _userName = MutableStateFlow(userPreferences.getUserName())
    val userName = _userName.asStateFlow()

    private val _profilePictureUri = MutableStateFlow<String?>(userPreferences.getProfilePictureUri())
    val profilePictureUri = _profilePictureUri.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    private val _isPrivateMode = MutableStateFlow(userPreferences.isPrivateModeEnabled())
    val isPrivateMode = _isPrivateMode.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _statusFilter = MutableStateFlow<StatusType?>(null)
    val statusFilter = _statusFilter.asStateFlow()

    val filteredClothes: StateFlow<List<ClothingItem>> = combine(
        allItems, searchQuery, statusFilter
    ) { items, query, status ->
        items.filter { item ->
            val matchesQuery = item.name.contains(query, ignoreCase = true) || 
                               item.category.contains(query, ignoreCase = true)
            val matchesStatus = status == null || item.status == status
            matchesQuery && matchesStatus
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setUserName(name: String) {
        val sanitized = name.replace(".", " ")
        val capitalizedName = sanitized.split(" ").filter { it.isNotBlank() }.joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
        val finalName = if (capitalizedName.isNotBlank()) capitalizedName else "Guest"
        _userName.value = finalName
        userPreferences.saveUserName(finalName)
    }

    fun setProfilePictureUri(uri: String?) {
        _profilePictureUri.value = uri
        userPreferences.saveProfilePictureUri(uri)
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setPrivateMode(enabled: Boolean) {
        _isPrivateMode.value = enabled
        userPreferences.setPrivateMode(enabled)
    }

    fun addItem(item: ClothingItem) {
        viewModelScope.launch {
            repository.addItem(item)
        }
    }

    fun updateItem(item: ClothingItem, previousItem: ClothingItem?) {
        viewModelScope.launch {
            repository.updateItem(item, previousItem)
        }
    }

    fun deleteItem(item: ClothingItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun restoreFromHistory(activity: HistoryActivity) {
        viewModelScope.launch {
            activity.previousState?.let {
                repository.restoreItem(it)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setStatusFilter(status: StatusType?) {
        _statusFilter.value = status
    }

    // Settings actions
    suspend fun exportDataToJson(): String {
        return repository.exportDataToJson()
    }

    fun importDataFromJson(json: String): Boolean {
        return try {
            viewModelScope.launch {
                repository.importDataFromJson(json)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAllData()
            userPreferences.clear()
            _userName.value = "Guest"
            _profilePictureUri.value = null
            _isPrivateMode.value = false
        }
    }

    class Factory(
        private val repository: ClothingRepository,
        private val userPreferences: UserPreferences
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClothingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ClothingViewModel(repository, userPreferences) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
