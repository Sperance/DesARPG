package ru.descend.desarpg.room.datas.items

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.descend.desarpg.logic.BaseItem
import ru.descend.desarpg.logic.EnumItemRarity
import ru.descend.desarpg.logic.EnumItemType

@Entity("items_table")
data class RoomItems(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    var name: String,
    var description: String = "",
    var rarity: EnumItemRarity = EnumItemRarity.DEFAULT,
    var type: EnumItemType,
    var count: Int = 1,
//    var paramsBool: ArrayList<StockStatsBool> = arrayListOf(),
//    var paramsValue: ArrayList<StockStatsValue> = arrayListOf()
)

fun BaseItem.toRoomItem() : RoomItems {
    return RoomItems(name = this.name, description = this.description, rarity = this.rarity, type = this.type, count = this.count)
}