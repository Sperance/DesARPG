package ru.descend.desarpg.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.log

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
) : IntEntityObjectClass {
    var mobInventory: ToOne<MobInventory>? = null

    fun getCategoryEnum() = EnumItemCategory.getFromName(category)
    fun getRarityEnum() = EnumItemRarity.getFromName(rarity)

    override fun saveToBox() {
        applicationBox.boxFor(BaseItem::class.java).put(this)
    }

    override fun toString(): String {
        return "BaseItem(id=$id, name='$name', description='$description', count=$count, category='$category', rarity='$rarity', price=$price)"
    }
}

@Entity
data class MobInventory(@Id override var id: Long = 0): IntEntityObjectClass {
    @Backlink(to = "mobInventory")
    lateinit var arrayStats: ToMany<BaseItem>

    fun addItem(item: BaseItem) {
        log("INVENTORY: ${hashCode()}")
        val findedItem = arrayStats.find { it.name == item.name }
        if (findedItem != null) {
            findedItem.count += item.count
            findedItem.saveToBox()
        }
        else {
            arrayStats.add(item)
            item.saveToBox()
        }
    }

    fun clearInventory() {
        arrayStats.clear()
        saveToBox()
    }

    override fun saveToBox() {
        arrayStats.forEach {
            applicationBox.boxFor(BaseItem::class.java).put(it)
        }
        applicationBox.boxFor(MobInventory::class.java).put(this)
    }

    override fun toString(): String {
        return "MobInventory(id=$id, arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}