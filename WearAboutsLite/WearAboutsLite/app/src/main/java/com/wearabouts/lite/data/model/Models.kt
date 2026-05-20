package com.wearabouts.lite.data.model

import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.data.local.HistoryAction

data class ClothingItem(
    val id: String,
    val name: String,
    val emoji: String,
    val category: String,
    val status: StatusType,
    val location: LocationType,
    val notes: String? = null,
    val borrowedBy: String? = null,
    val photoUri: String? = null,
    val lastUpdated: Long
)

data class HistoryActivity(
    val id: String,
    val itemId: String,
    val itemName: String,
    val itemEmoji: String,
    val action: HistoryAction,
    val status: StatusType? = null,
    val location: LocationType? = null,
    val changes: String? = null,
    val previousState: ClothingItem? = null,
    val timestamp: Long
)
