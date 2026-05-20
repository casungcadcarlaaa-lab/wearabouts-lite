package com.wearabouts.lite.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {
    @Query("SELECT * FROM clothing_items ORDER BY lastUpdated DESC")
    fun getAllItems(): Flow<List<ClothingItemEntity>>

    @Query("SELECT * FROM clothing_items WHERE id = :id")
    suspend fun getItemById(id: String): ClothingItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ClothingItemEntity)

    @Update
    suspend fun updateItem(item: ClothingItemEntity)

    @Delete
    suspend fun deleteItem(item: ClothingItemEntity)

    @Query("DELETE FROM clothing_items")
    suspend fun clearAllItems()
}
