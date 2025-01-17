package ru.descend.desarpg.room.datas.items

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.descend.desarpg.log
import ru.descend.desarpg.logic.EnumItemRarity
import ru.descend.desarpg.logic.EnumItemType
import ru.descend.desarpg.logic.StockSimpleStat
import ru.descend.desarpg.logic.StockStatsBool
import ru.descend.desarpg.logic.StockStatsValue
import kotlin.reflect.KClass

@Entity("items_table")
data class RoomItems(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    var name: String,
    var description: String = "",
    var rarity: EnumItemRarity = EnumItemRarity.DEFAULT,
    var type: EnumItemType,
    var count: Int = 1,
    var paramsBool: ArrayList<StockStatsBool> = arrayListOf(),
    var paramsValue: ArrayList<StockStatsValue> = arrayListOf()
) {
    /***BOOL STATS***/
    fun getBool(classBool: KClass<out StockStatsBool>): StockStatsBool? {
        return paramsBool.find { it::class == classBool }
    }
    fun getBoolResult(classBool: KClass<out StockStatsBool>): Boolean? {
        return paramsBool.find { it::class == classBool }?.value
    }
    fun containsBool(classBool: KClass<out StockStatsBool>): Boolean {
        return getBool(classBool) != null
    }
    
    /***VALUE STATS***/
    fun getValue(classValue: KClass<out StockStatsValue>): StockStatsValue? {
        return paramsValue.find { it::class == classValue }
    }
    fun getValueResult(classValue: KClass<out StockStatsValue>): Int? {
        return paramsValue.find { it::class == classValue }?.value
    }
    fun containsValue(classBool: KClass<out StockStatsValue>): Boolean {
        return getValue(classBool) != null
    }

    /***CUSTOM METHODS***/
    fun <T> addParam(param: StockSimpleStat<T>) {
        when (param) {
            is StockStatsBool -> {
                val currentParam = getBool((param as StockStatsBool)::class)
                if (currentParam == null) paramsBool.add(param)
                else log("[StockStatsBool already exists] item: $name param: $param")
            }
            is StockStatsValue -> {
                val currentParam = getValue((param as StockStatsValue)::class)
                if (currentParam == null) paramsValue.add(param)
                else currentParam.value += param.value
            }
        }
    }
    fun <T> setParam(param: StockSimpleStat<T>) {
        when (param) {
            is StockStatsBool -> {
                getBool((param as StockStatsBool)::class)?.let { value ->
                    value.value = param.value
                }
            }
            is StockStatsValue -> {
                getValue((param as StockStatsValue)::class)?.let { value ->
                    value.value = param.value
                }
            }
        }
    }
}