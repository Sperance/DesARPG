package ru.descend.desarpg.room.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.descend.desarpg.logic.StockStatsBool

class StockStatsBoolConverter {
    @TypeConverter
    fun fromStockStatsBool(stats: ArrayList<StockStatsBool>): String {
        return Json.encodeToString(stats)
    }

    @TypeConverter
    fun toStockStatsBool(data: String): ArrayList<StockStatsBool> {
        return Json.decodeFromString<ArrayList<StockStatsBool>>(data)
    }
}