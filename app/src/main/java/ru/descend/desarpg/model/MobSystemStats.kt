package ru.descend.desarpg.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.to1Digits

enum class EnumSystemStatsType(val statName: String, val baseValue: Double) {
    UNDEFINED("", 0.0),
    EXPERIENCE("Опыт", 0.0),
    LAST_ENTER_DATE("Дата последнего входа", System.currentTimeMillis().toDouble()),
    COUNT_ENTER_SYSTEM("Количество входов в систему", 0.0),
}

@Entity
open class SystemStatsProp(
    @Id override var id: Long = 0,
    var type: String = "",
    var description: String = "",
    var valueProp: Double = 0.0,
    var percentProp: Double = 0.0,
): AbsEntityBase<SystemStatsProp> {
    var mobSystemStats: ToOne<MobSystemStats>? = null

    fun add(count: Number) : SystemStatsProp {
        valueProp = (valueProp + count.toDouble()).to1Digits()
        return this
    }
    fun rem(count: Number) : SystemStatsProp {
        valueProp = (valueProp - count.toDouble()).to1Digits()
        return this
    }
    fun set(count: Number) : SystemStatsProp {
        valueProp = count.toDouble().to1Digits()
        return this
    }

    override fun getClassObj(): Class<SystemStatsProp> = SystemStatsProp::class.java
    override fun targetLinkToOne(): ToOne<*>? = mobSystemStats
    override fun toString(): String {
        return "SystemStatsProp(id=$id, type=$type, description='$description', valueProp=$valueProp, mobSystemStats=${mobSystemStats?.target?.id})"
    }
}

@Entity
data class MobSystemStats(@Id override var id: Long = 0): AbsEntityBase<MobSystemStats> {
    @Backlink(to = "mobSystemStats")
    lateinit var arrayStats: ToMany<SystemStatsProp>

    fun initializeAllStats() {
        arrayStats.clear()
        EnumSystemStatsType.entries.filter { it != EnumSystemStatsType.UNDEFINED }.forEach {
            arrayStats.add(SystemStatsProp().apply { type = it.name ; valueProp = it.baseValue })
        }
    }

    fun getStockStat(stat: EnumSystemStatsType): SystemStatsProp {
        return arrayStats.find { it.type == stat.name }!!
    }

    fun getStockStat(stat: String): SystemStatsProp {
        return arrayStats.find { it.type == stat }!!
    }

    override fun getClassObj(): Class<MobSystemStats> = MobSystemStats::class.java
    override fun saveToBox() {
        arrayStats.forEach {
            it.saveToBox()
        }
        super.saveToBox()
    }
    override fun toString(): String {
        return "MobSystemStats(id=$id, arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}