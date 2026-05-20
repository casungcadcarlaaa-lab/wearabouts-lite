package com.wearabouts.lite.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wearabouts.lite.data.local.*
import com.wearabouts.lite.data.model.ClothingItem
import com.wearabouts.lite.data.model.HistoryActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

class ClothingRepository(
    private val clothingDao: ClothingDao,
    private val historyDao: HistoryDao
) {
    private val gson = Gson()

    val allItems: Flow<List<ClothingItem>> = clothingDao.getAllItems().map { entities ->
        entities.map { it.toDomain() }
    }

    val allHistory: Flow<List<HistoryActivity>> = historyDao.getAllActivities().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun addItem(item: ClothingItem) {
        clothingDao.insertItem(item.toEntity())
        val activity = HistoryActivityEntity(
            id = UUID.randomUUID().toString(),
            itemId = item.id,
            itemName = item.name,
            itemEmoji = item.emoji,
            action = HistoryAction.Added,
            status = item.status,
            location = item.location,
            timestamp = System.currentTimeMillis()
        )
        historyDao.insertActivity(activity)
    }

    suspend fun updateItem(item: ClothingItem, previousItem: ClothingItem?) {
        clothingDao.updateItem(item.toEntity())
        
        val changes = mutableListOf<String>()
        previousItem?.let { prev ->
            if (item.status != prev.status) changes.add("Status: ${prev.status} -> ${item.status}")
            if (item.location != prev.location) changes.add("Location: ${prev.location} -> ${item.location}")
        }
        
        val activity = HistoryActivityEntity(
            id = UUID.randomUUID().toString(),
            itemId = item.id,
            itemName = item.name,
            itemEmoji = item.emoji,
            action = HistoryAction.Updated,
            status = item.status,
            location = item.location,
            changes = if (changes.isNotEmpty()) changes.joinToString(", ") else null,
            previousStateJson = previousItem?.let { gson.toJson(it) },
            timestamp = System.currentTimeMillis()
        )
        historyDao.insertActivity(activity)
    }

    suspend fun deleteItem(item: ClothingItem) {
        clothingDao.deleteItem(item.toEntity())
        val activity = HistoryActivityEntity(
            id = UUID.randomUUID().toString(),
            itemId = item.id,
            itemName = item.name,
            itemEmoji = item.emoji,
            action = HistoryAction.Deleted,
            status = item.status,
            location = item.location,
            previousStateJson = gson.toJson(item),
            timestamp = System.currentTimeMillis()
        )
        historyDao.insertActivity(activity)
    }

    suspend fun restoreItem(item: ClothingItem) {
        clothingDao.insertItem(item.toEntity())
        val activity = HistoryActivityEntity(
            id = UUID.randomUUID().toString(),
            itemId = item.id,
            itemName = item.name,
            itemEmoji = item.emoji,
            action = HistoryAction.Restored,
            status = item.status,
            location = item.location,
            timestamp = System.currentTimeMillis()
        )
        historyDao.insertActivity(activity)
    }

    suspend fun getItemById(id: String): ClothingItem? {
        return clothingDao.getItemById(id)?.toDomain()
    }

    suspend fun exportDataToJson(): String {
        val items = allItems.first()
        val data = mapOf("items" to items)
        return gson.toJson(data)
    }

    suspend fun importDataFromJson(json: String) {
        val type = object : TypeToken<Map<String, List<ClothingItem>>>() {}.type
        val data: Map<String, List<ClothingItem>> = gson.fromJson(json, type)
        data["items"]?.forEach { item ->
            clothingDao.insertItem(item.toEntity())
        }
    }

    suspend fun clearAllData() {
        clothingDao.clearAllItems()
        historyDao.clearAllActivities()
    }

    // Mapper extensions
    private fun ClothingItemEntity.toDomain() = ClothingItem(
        id = id, name = name, emoji = emoji, category = category,
        status = status, location = location, notes = notes,
        borrowedBy = borrowedBy, photoUri = photoUri, lastUpdated = lastUpdated
    )

    private fun ClothingItem.toEntity() = ClothingItemEntity(
        id = id, name = name, emoji = emoji, category = category,
        status = status, location = location, notes = notes,
        borrowedBy = borrowedBy, photoUri = photoUri, lastUpdated = lastUpdated
    )

    private fun HistoryActivityEntity.toDomain() = HistoryActivity(
        id = id, itemId = itemId, itemName = itemName, itemEmoji = itemEmoji,
        action = action,
        status = status,
        location = location,
        changes = changes,
        previousState = previousStateJson?.let { gson.fromJson(it, ClothingItem::class.java) },
        timestamp = timestamp
    )
}
