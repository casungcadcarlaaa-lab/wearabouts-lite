package com.wearabouts.lite.data.local

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromStatusType(value: StatusType?): String? = value?.name

    @TypeConverter
    fun toStatusType(value: String?): StatusType? = value?.let { StatusType.valueOf(it) }

    @TypeConverter
    fun fromLocationType(value: LocationType?): String? = value?.name

    @TypeConverter
    fun toLocationType(value: String?): LocationType? = value?.let { LocationType.valueOf(it) }

    @TypeConverter
    fun fromHistoryAction(value: HistoryAction): String = value.name

    @TypeConverter
    fun toHistoryAction(value: String): HistoryAction = HistoryAction.valueOf(value)

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
