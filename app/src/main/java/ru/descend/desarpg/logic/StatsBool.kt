package ru.descend.desarpg.logic

import ru.descend.desarpg.log

abstract class StockSimpleStat<T> (
    var description: String = ""
) {
    abstract val name: String
    abstract var value: T
    abstract val code: String

    abstract fun get() : T
    abstract fun set(newValue: T)

    override fun toString(): String {
        return "StockSimpleStat(code='$code', name='$name', value=$value)"
    }
}

sealed class StockStatsBool(
    override val name: String,
    override val code: String,
) : StockSimpleStat<Boolean>() {
    override fun get() = value
    override fun set(newValue: Boolean) {
        value = newValue
    }
}

sealed class StockStatsValue(
    override val name: String,
    override val code: String,
) : StockSimpleStat<Int>() {
    override fun get() = value
    override fun set(newValue: Int) {
        value = newValue
        if (value < 0) value = 0
    }
}

sealed class StockSimpleStatsBool {
    class IsEquipped(override var value: Boolean) : StockStatsBool("Экипирован", "bEQ")
    class IsCanSell(override var value: Boolean) : StockStatsBool("Можно продавать", "bCS")
    class IsCanTrade(override var value: Boolean) : StockStatsBool("Можно торговать", "bCT")
}

sealed class StockSimpleStatsValue {
    class MaxPrefix(override var value: Int) : StockStatsValue("Максимальное кол-во префиксов", "vMaxPref")
    class MaxSuffix(override var value: Int) : StockStatsValue("Максимальное кол-во суффиксов", "vMaxSuf")
    class MaxPostfix(override var value: Int) : StockStatsValue("Максимальное кол-во постфиксов", "vMaxPost")
}