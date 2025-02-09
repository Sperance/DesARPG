package ru.descend.desarpg.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import ru.descend.desarpg.model.converters.EnumSystemStatsTypeConverter
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
data class SystemStatsProp(
    @Id var id: Long = 0,
    @Convert(converter = EnumSystemStatsTypeConverter::class, dbType = String::class)
    var type: EnumSystemStatsType = EnumSystemStatsType.UNDEFINED,
    var description: String = "",
    var value: Double = 0.0
) {
    fun add(count: Number) {
        value = (value + count.toDouble()).to1Digits()
    }
    fun rem(count: Number) {
        value = (value - count.toDouble()).to1Digits()
    }
    fun set(count: Number) {
        value = count.toDouble().to1Digits()
    }
}

@Entity
data class MobSystemStats(@Id var id: Long = 0) {
    lateinit var arrayStats: ToMany<SystemStatsProp>

    fun initializeAllStats() {
        arrayStats.clear()
        EnumSystemStatsType.entries.filter { it != EnumSystemStatsType.UNDEFINED }.forEach {
            arrayStats.add(SystemStatsProp(type = it, value = it.baseValue))
        }
    }

    fun getStockStat(stat: EnumSystemStatsType): SystemStatsProp {
        return arrayStats.find { it.type == stat }!!
    }

    override fun toString(): String {
        return "MobSystemStats(id=$id, arrayStats=${arrayStats.joinToString()})"
    }
}