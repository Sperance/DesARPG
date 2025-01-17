package ru.descend.desarpg.room.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.descend.desarpg.logic.StockStatsValue

class StockStatsValueConverter {
    @TypeConverter
    fun fromStockStatsValue(stats: ArrayList<StockStatsValue>): String {
        return Json.encodeToString(stats)
    }

    @TypeConverter
    fun toStockStatsValue(data: String): ArrayList<StockStatsValue> {
        return Json.decodeFromString<ArrayList<StockStatsValue>>(data)
    }
}