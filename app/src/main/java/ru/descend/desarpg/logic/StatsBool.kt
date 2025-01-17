package ru.descend.desarpg.logic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
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

@Serializable
sealed class StockStatsBool(
    override val name: String,
    override val code: String,
) : StockSimpleStat<Boolean>() {
    override fun get() = value
    override fun set(newValue: Boolean) {
        value = newValue
    }
}

@Serializable
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
    @Serializable class IsEquipped(override var value: Boolean) : StockStatsBool("Экипирован", "bEQ")
    @Serializable class IsCanSell(override var value: Boolean) : StockStatsBool("Можно продавать", "bCS")
    @Serializable class IsCanTrade(override var value: Boolean) : StockStatsBool("Можно торговать", "bCT")
}

sealed class StockSimpleStatsValue {
    @Serializable class MaxPrefix(override var value: Int) : StockStatsValue("Максимальное кол-во префиксов", "vMaxPref")
    @Serializable class MaxSuffix(override var value: Int) : StockStatsValue("Максимальное кол-во суффиксов", "vMaxSuf")
    @Serializable class MaxPostfix(override var value: Int) : StockStatsValue("Максимальное кол-во постфиксов", "vMaxPost")
}

//interface StockStatsBoolSerializer<T: StockStatsBool> : KSerializer<T> {
//    override val descriptor: SerialDescriptor get() = PrimitiveSerialDescriptor("StockStatsBool", PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: T) {
//        val string = value.code + ":" + value.value
//        encoder.encodeString(string)
//    }
//
//    override fun deserialize(decoder: Decoder): T {
//        val string = decoder.decodeString().split(":")
//        return StockStatsBool.
//    }
//}