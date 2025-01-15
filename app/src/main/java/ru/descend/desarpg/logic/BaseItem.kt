package ru.descend.desarpg.logic

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlinx.serialization.Serializable
import ru.descend.desarpg.room.datas.items.RoomItems

@Serializable
enum class EnumItemRarity(val rarityName: String, @ColorInt val rarityColor: Int) {
    DEFAULT("Обычный", Color.WHITE),
    MAGIC("Магический", Color.BLUE),
    RARITY("Редкий", Color.YELLOW),
    UNIQUE("Уникальный", Color.RED)
}

@Serializable
enum class EnumItemType(val typeName: String) {
    ITEM("Предмет"),
    EQUIPMENT("Экипировка")
}

@Serializable
class BaseItem(
    var name: String,
    var rarity: EnumItemRarity,
    var type: EnumItemType
    ) {

    var count = 1
    var description: String = ""
    var paramsBool: ArrayList<StockStatsBool> = arrayListOf()
    var paramsValue: ArrayList<StockStatsValue> = arrayListOf()

    override fun toString(): String {
        return "BaseItem(name='$name', rarity=$rarity, description='$description', paramsBool=$paramsBool, paramsValue=$paramsValue)"
    }
}

class InventoryMob {
    private val arrayItems = ArrayList<RoomItems>()

    fun getAll() = arrayItems

    fun clearInventory() {
        arrayItems.clear()
    }

    fun addToInventory(item: RoomItems) {
        val findedItem = arrayItems.find { it.name == item.name }
        if (findedItem != null) {
            findedItem.count++
        } else {
            arrayItems.add(item)
        }
    }

    fun setInventory(items: Collection<RoomItems>) {
        arrayItems.clear()
        arrayItems.addAll(items)
    }
}