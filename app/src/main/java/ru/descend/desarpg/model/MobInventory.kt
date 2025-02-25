package ru.descend.desarpg.model

import io.objectbox.Box
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.log
import java.util.UUID

enum class EnumItemCategory(val categoryName: String) {
    DEFAULT(""),
    CONSUMABLE("Расходный материал"),
    EQUIPMENT("Экипировка"),
    RESOURCE("Предмет");

    companion object {
        fun getFromName(name: String) : EnumItemCategory? {
            return entries.find { it.name == name }
        }
    }
}

enum class EnumItemRarity(val rarityName: String) {
    DEFAULT(""),
    COMMON("Обычный"),
    UNCOMMON("Необычный"),
    RARE("Редкий"),
    UNIQUE("Уникальный");

    companion object {
        fun getFromName(name: String) : EnumItemRarity? {
            return entries.find { it.name == name }
        }
    }
}

@Entity
open class BaseItem(
    @Id override var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var count: Long = 1,
    var category: String = EnumItemCategory.DEFAULT.name,
    var rarity: String = EnumItemRarity.DEFAULT.name,
    var price: Double = 1.0
) : AbsEntityBase<BaseItem>(applicationBox.boxFor(BaseItem::class.java)) {
    var mobInventory: ToOne<MobInventory>? = null
    override fun targetLinkToOne() = mobInventory

    fun getCategoryEnum() = EnumItemCategory.getFromName(category)
    fun getRarityEnum() = EnumItemRarity.getFromName(rarity)

    override fun toString(): String {
        return "BaseItem(id=$id, name='$name', description='$description', count=$count, category='$category', rarity='$rarity', price=$price, inv=${mobInventory?.target?.uuid})"
    }
}

@Entity
data class MobInventory(
    @Id override var id: Long = 0,
    var uuid: String = UUID.randomUUID().toString()
): AbsEntityBase<MobInventory>(applicationBox.boxFor(MobInventory::class.java)) {
    @Backlink(to = "mobInventory")
    lateinit var arrayStats: ToMany<BaseItem>

    fun addItem(item: BaseItem) {
        val findedItem = arrayStats.find { it.name == item.name }
        if (findedItem != null) {
            findedItem.count += item.count
            findedItem.saveToBox()
        }
        else {
            arrayStats.add(item)
            item.mobInventory?.target = this
            item.saveToBox()
        }
    }

    fun clearInventory() {
        for (item in arrayStats) {
            item.removeFromBox()
        }
        arrayStats.clear()
        saveToBox()
    }

    override fun toString(): String {
        return "MobInventory(id=$id, uuid=$uuid, boxItemsCount=${applicationBox.boxFor(BaseItem::class.java).count()} arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}