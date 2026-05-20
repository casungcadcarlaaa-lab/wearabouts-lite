package com.wearabouts.lite.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_activities ORDER BY timestamp DESC")
    fun getAllActivities(): Flow<List<HistoryActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: HistoryActivityEntity)

    @Query("SELECT * FROM history_activities WHERE id = :id")
    suspend fun getActivityById(id: String): HistoryActivityEntity?

    @Query("DELETE FROM history_activities WHERE id = :id")
    suspend fun deleteActivity(id: String)

    @Query("DELETE FROM history_activities")
    suspend fun clearAllActivities()
}
