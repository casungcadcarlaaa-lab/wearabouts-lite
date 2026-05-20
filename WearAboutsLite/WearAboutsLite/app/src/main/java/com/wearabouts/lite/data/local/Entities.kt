package com.wearabouts.lite.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class StatusType { Clean, Used, Borrowed, Dirty }
enum class LocationType { Cabinet, LaundryBasket, CurrentlyWearing, Borrowed }
enum class HistoryAction { Added, Updated, Deleted, Restored }

@Entity(tableName = "clothing_items")
data class ClothingItemEntity(
    @PrimaryKey
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

@Entity(tableName = "history_activities")
data class HistoryActivityEntity(
    @PrimaryKey
    val id: String,
    val itemId: String,
    val itemName: String,
    val itemEmoji: String,
    val action: HistoryAction,
    val status: StatusType? = null,
    val location: LocationType? = null,
    val changes: String? = null,
    val previousStateJson: String? = null,
    val timestamp: Long
)
