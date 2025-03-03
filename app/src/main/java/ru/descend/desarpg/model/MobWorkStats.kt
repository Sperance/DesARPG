package ru.descend.desarpg.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.logic.randomizers.randInt
import ru.descend.desarpg.logic.randomizers.randLong

data class WorkLeveledItem(
    val codeIndex: Int,
    val itemName: String,
    val requireMiningLevel: Int,
    val expForTick: IntRange,
    val expForMined: IntRange,
    var isSelected: Boolean = false
)

val miningResources = listOf(
    WorkLeveledItem(101, "Каменная пыль", 1, 20..55, 1..3),
    WorkLeveledItem(102, "Угольная пыль", 3, 5..8, 3..5),
    WorkLeveledItem(103, "Бронзовая пыль", 5, 8..14, 5..8),
    WorkLeveledItem(104, "Железная пыль", 7, 10..20, 8..12),
    WorkLeveledItem(105, "Золотая пыль", 9, 15..25, 12..16),
    WorkLeveledItem(106, "Алмазная пыль", 11, 20..30, 16..20),
    WorkLeveledItem(107, "Редкая металлическая пыль", 13, 25..35, 20..25),
    WorkLeveledItem(108, "Мифическая металлическая пыль", 15, 30..40, 25..30),
    WorkLeveledItem(109, "Энергетическая кристальная пыль", 17, 35..45, 30..35),
    WorkLeveledItem(110, "Астральная каменная пыль", 20, 40..50, 35..40)
)

val fishingResources = listOf(
    WorkLeveledItem(101, "Малая рыба", 1, 5..10, 1..2),
    WorkLeveledItem(102, "Карась", 3, 8..15, 2..4),
    WorkLeveledItem(103, "Окунь", 5, 10..20, 4..6),
    WorkLeveledItem(104, "Щука", 7, 15..25, 6..8),
    WorkLeveledItem(105, "Лещ", 9, 20..30, 8..10),
    WorkLeveledItem(106, "Плотва", 11, 25..35, 10..12),
    WorkLeveledItem(107, "Судак", 13, 30..40, 12..14),
    WorkLeveledItem(108, "Крокодил", 15, 35..45, 14..16),
    WorkLeveledItem(109, "Щука-крокодил", 17, 40..50, 16..18),
    WorkLeveledItem(110, "Лохнесское Чудовище", 20, 45..55, 18..20)
)

enum class EnumWorkStatsType(val statName: String, val listItems: List<WorkLeveledItem>) {
    UNDEFINED("", listOf()),
    MINING("Шахта", miningResources),
    FISHING("Рыболовля", fishingResources);

    companion object {
        fun getFromName(statName: String) : EnumWorkStatsType? {
            return EnumWorkStatsType.entries.find { it.name == statName }
        }
        fun getSelectedObject(prop: WorkStatsProp) : WorkLeveledItem? {
            if (prop.selectedObject == -1) return null
            return prop.getTypeEnum()?.listItems?.firstOrNull { it.codeIndex == prop.selectedObject }
        }
    }
}

@Entity
open class WorkStatsProp(
    @Id override var id: Long = 0,
    var type: String = "",
    var description: String = "",

    var globalLevelWork: Int = 1,
    var currentExperience: Double = 0.0,
    var needNextLevelExperience: Double = 100.0,
    var countGainedItems: Double = 0.0,
    var selectedObject: Int = 101

): AbsEntityBase<WorkStatsProp> {
    var mobWorkStats: ToOne<MobWorkStats>? = null

    fun getTypeEnum() = EnumWorkStatsType.getFromName(type)
    fun getActiveLevels(): ArrayList<WorkLeveledItem> {
        val list = ArrayList<WorkLeveledItem>()
        getTypeEnum()?.listItems?.forEach {
            if (globalLevelWork >= it.requireMiningLevel)
                list.add(it)
        }
        return list
    }
    fun getMiningItem(): BaseItem {
        val item = BaseItem(
            name = EnumWorkStatsType.getSelectedObject(this)!!.itemName,
            description = "Простая добыча",
            count = randLong(1, 2),
            category = EnumItemCategory.RESOURCE.name,
            rarity = EnumItemRarity.COMMON.name
        )
        return item
    }
    fun minedTick(item: BaseItem) {
        val leveled = EnumWorkStatsType.getSelectedObject(this)!!
        currentExperience += randInt(leveled.expForMined) * item.count
        countGainedItems += item.count
        if (currentExperience >= needNextLevelExperience) {
            globalLevelWork++
            needNextLevelExperience = 100.0 * globalLevelWork
        }
    }

    override fun getClassObj(): Class<WorkStatsProp> = WorkStatsProp::class.java
    override fun targetLinkToOne(): ToOne<*>? = mobWorkStats
    override fun toString(): String {
        return "WorkStatsProp(id=$id, type='$type', description='$description', globalLevelWork=$globalLevelWork, currentExperience=$currentExperience, needNextLevelExperience=$needNextLevelExperience, countGainedItems=$countGainedItems, selectedObject=$selectedObject)"
    }
}

@Entity
data class MobWorkStats(@Id override var id: Long = 0): AbsEntityBase<MobWorkStats> {
    @Backlink(to = "mobWorkStats")
    lateinit var arrayStats: ToMany<WorkStatsProp>

    fun initializeAllStats() {
        arrayStats.clear()
        EnumWorkStatsType.entries.filter { it != EnumWorkStatsType.UNDEFINED }.forEach {
            arrayStats.add(WorkStatsProp().apply { type = it.name })
        }
    }

    fun getStockStat(stat: EnumWorkStatsType): WorkStatsProp {
        return arrayStats.find { it.type == stat.name }!!
    }

    override fun getClassObj(): Class<MobWorkStats> = MobWorkStats::class.java
    override fun saveToBox() {
        arrayStats.forEach { it.saveToBox() }
        super.saveToBox()
    }
    override fun toString(): String {
        return "MobWorkStats(id=$id, arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}