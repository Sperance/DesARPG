package ru.descend.desarpg.logic

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlinx.serialization.Serializable

@Serializable
enum class EnumItemRarity(val rarityName: String, @ColorInt val rarityColor: Int) {
    DEFAULT("Обычный", Color.WHITE),
    MAGIC("Магический", Color.BLUE),
    RARITY("Редкий", Color.YELLOW),
    UNIQUE("Уникальный", Color.RED)
}

@Serializable
sealed class BaseItem {
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

@Serializable
class InventoryMob {
    @Serializable
    private val arrayItems = ArrayList<BaseItem>()

    fun clearInventory() {
        arrayItems.clear()
    }

    fun addToInventory(item: BaseItem) {
        arrayItems.add(item)
    }
}