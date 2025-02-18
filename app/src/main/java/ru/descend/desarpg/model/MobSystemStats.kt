package ru.descend.desarpg.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.to1Digits

enum class EnumSystemStatsCategory {
    UNDEFINED,
    MOB_MAIN,
    MOB_ADDITIONAL
}

enum class EnumSystemStatsType(val statName: String, val category: EnumSystemStatsCategory, val baseValue: Double) {
    UNDEFINED("", EnumSystemStatsCategory.UNDEFINED,.0),

    EXPERIENCE("Опыт", EnumSystemStatsCategory.MOB_MAIN,0.0),

    LAST_ENTER_DATE("Дата последнего входа",
        EnumSystemStatsCategory.MOB_ADDITIONAL, System.currentTimeMillis().toDouble()),
    COUNT_ENTER_SYSTEM("Количество входов в систему", EnumSystemStatsCategory.MOB_ADDITIONAL, 0.0)
}

@Entity
open class SystemStatsProp: DoubleProp(), IntEntityObjectClass {
    var mobSystemStats: ToOne<MobSystemStats>? = null

    fun add(count: Number) {
        valueProp = (valueProp + count.toDouble()).to1Digits()
    }
    fun rem(count: Number) {
        valueProp = (valueProp - count.toDouble()).to1Digits()
    }
    fun set(count: Number) {
        valueProp = count.toDouble().to1Digits()
    }

    override fun saveToBox() {
        applicationBox.boxFor(SystemStatsProp::class.java).put(this)
    }

    override fun toString(): String {
        return "SystemStatsProp(id=$id, type=$type, description='$description', valueProp=$valueProp, mobSystemStats=${mobSystemStats?.target?.id})"
    }
}

@Entity
data class MobSystemStats(@Id override var id: Long = 0): IntEntityObjectClass {
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

    override fun saveToBox() {
        arrayStats.forEach {
            applicationBox.boxFor(SystemStatsProp::class.java).put(it)
        }
        applicationBox.boxFor(MobSystemStats::class.java).put(this)
    }

    override fun toString(): String {
        return "MobSystemStats(id=$id, arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}