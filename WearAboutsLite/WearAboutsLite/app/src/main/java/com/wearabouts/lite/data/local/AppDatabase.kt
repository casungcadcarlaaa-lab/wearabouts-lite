package com.wearabouts.lite.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Database(entities = [ClothingItemEntity::class, HistoryActivityEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clothingDao(): ClothingDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wearabouts_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(DatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.clothingDao())
                    }
                }
            }

            suspend fun populateDatabase(clothingDao: ClothingDao) {
                val seedItems = listOf(
                    ClothingItemEntity(UUID.randomUUID().toString(), "White Polo Shirt", "👕", "Tops", StatusType.Clean, LocationType.Cabinet, null, null, null, System.currentTimeMillis()),
                    ClothingItemEntity(UUID.randomUUID().toString(), "Blue Jeans", "👖", "Bottoms", StatusType.Clean, LocationType.Cabinet, null, null, null, System.currentTimeMillis()),
                    ClothingItemEntity(UUID.randomUUID().toString(), "Red Hoodie", "🧥", "Outerwear", StatusType.Used, LocationType.LaundryBasket, null, null, null, System.currentTimeMillis()),
                    ClothingItemEntity(UUID.randomUUID().toString(), "Black Sneakers", "👟", "Footwear", StatusType.Clean, LocationType.Cabinet, null, null, null, System.currentTimeMillis()),
                    ClothingItemEntity(UUID.randomUUID().toString(), "Gray Sweatpants", "🩳", "Bottoms", StatusType.Used, LocationType.LaundryBasket, null, null, null, System.currentTimeMillis()),
                    ClothingItemEntity(UUID.randomUUID().toString(), "Leather Jacket", "🧥", "Outerwear", StatusType.Borrowed, LocationType.Borrowed, null, "Sarah", null, System.currentTimeMillis())
                )
                seedItems.forEach { clothingDao.insertItem(it) }
            }
        }
    }
}
