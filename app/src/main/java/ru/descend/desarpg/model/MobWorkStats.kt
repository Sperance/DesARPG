package ru.descend.desarpg.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.logic.randomizers.randInt
import ru.descend.desarpg.logic.randomizers.randLong

data class WorkLeveledItem(
    val itemName: String,
    val requireMiningLevel: Int,
    val expForTick: IntRange,
    val expForMined: IntRange
)

enum class EnumWorkStatsType(val statName: String, val listItems: List<WorkLeveledItem>) {
    UNDEFINED("", listOf()),
    MINING("Шахта", listOf(
        WorkLeveledItem("Камень", 1, 20..55, 1..3),
        WorkLeveledItem("Уголь", 3, 5..8, 3..5),
        WorkLeveledItem("Бронза", 5, 8..14, 5..8),
    )),
    FISHING("Рыболовля", listOf(
        WorkLeveledItem("Малек", 1, 2..5, 1..3)
    ));

    companion object {
        fun getFromName(statName: String) : EnumWorkStatsType? {
            return EnumWorkStatsType.entries.find { it.name == statName }
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
    var countGainedItems: Double = 0.0

): AbsEntityBase<WorkStatsProp> {
    var mobWorkStats: ToOne<MobWorkStats>? = null

    fun getTypeEnum() = EnumWorkStatsType.getFromName(type)
    fun getCurrentLevel(): WorkLeveledItem {
        val curLevel = getTypeEnum()?.listItems?.lastOrNull { globalLevelWork >= it.requireMiningLevel }
        if (curLevel == null) throw IllegalArgumentException("curLevel is null")
        return curLevel
    }
    fun getMiningRange(): IntRange {
        return getCurrentLevel().expForTick
    }
    fun getMiningItem(): BaseItem {
        val item = BaseItem(
            name = getCurrentLevel().itemName,
            description = "Простая добыча",
            count = randLong(1, 2),
            category = EnumItemCategory.RESOURCE.name,
            rarity = EnumItemRarity.COMMON.name
        )
        return item
    }
    fun minedTick(item: BaseItem) {
        val leveled = getCurrentLevel()
        currentExperience += randInt(leveled.expForMined) * item.count
        countGainedItems += item.count
        if (currentExperience >= needNextLevelExperience) {
            globalLevelWork++
            needNextLevelExperience = 100.0 * globalLevelWork
        }
    }

    override fun getClassObj(): Class<WorkStatsProp> = WorkStatsProp::class.java
    override fun targetLinkToOne(): ToOne<*>? = mobWorkStats
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