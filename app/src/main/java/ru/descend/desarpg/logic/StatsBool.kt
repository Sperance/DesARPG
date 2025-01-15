package ru.descend.desarpg.logic

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class StockSimpleStat<T> (
    var description: String = ""
) {
    abstract val name: String
    abstract var value: T

    abstract fun get() : T
    abstract fun set(newValue: T)

    override fun toString(): String {
        return "StockSimpleStat(description='$description', name='$name', value=$value)"
    }
}

@Serializable
sealed class StockStatsBool(
    override val name: String,
) : StockSimpleStat<Boolean>() {
    override fun get() = value
    override fun set(newValue: Boolean) {
        value = newValue
    }
}

@Serializable
sealed class StockStatsValue(
    override val name: String
) : StockSimpleStat<Int>() {
    override fun get() = value
    override fun set(newValue: Int) {
        value = newValue
        if (value < 0) value = 0
    }
}

@Serializable
sealed class StockSimpleStatsBool {
    @Serializable class IsEquipped(override var value: Boolean) : StockStatsBool("Экипирован")
    @Serializable class IsCanSell(override var value: Boolean) : StockStatsBool("Можно продавать")
    @Serializable class IsCanTrade(override var value: Boolean) : StockStatsBool("Можно торговать")
}

@Serializable
sealed class StockSimpleStatsValue {
    @Serializable class MaxPrefix(override var value: Int) : StockStatsValue("Максимальное кол-во префиксов")
    @Serializable class MaxSuffix(override var value: Int) : StockStatsValue("Максимальное кол-во суффиксов")
    @Serializable class MaxPostfix(override var value: Int) : StockStatsValue("Максимальное кол-во постфиксов")
}