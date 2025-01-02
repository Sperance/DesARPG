package ru.descend.desarpg.logic

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
enum class EnumItemRarity(rarityName: String) {
    DEFAULT("Обычный"),
    MAGIC("Магический"),
    RARITY("Редкий"),
    UNIQUE("Уникальный")
}

@Serializable
abstract class BaseItem {
    abstract val name: String
    abstract var rarity: EnumItemRarity
    var description: String = ""
    var paramsBool: ArrayList<StockStatsBool> = arrayListOf()
    var paramsValue: ArrayList<StockStatsValue> = arrayListOf()

    override fun toString(): String {
        return "BaseItem(name='$name', rarity=$rarity, description='$description', paramsBool=$paramsBool, paramsValue=$paramsValue)"
    }
}

@Serializable
class EquippingItem(override val name: String, override var rarity: EnumItemRarity) : BaseItem()

@Serializable
class SimpleItem(override val name: String, override var rarity: EnumItemRarity) : BaseItem()