package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

enum class EnumPropsType(val statName: String, val baseValue: Double) {
    UNDEFINED("", 0.0),
    HEALTH("Здоровье", 100.0),
    PHYSIC_ATTACK("Физ. атака", 5.0),
    STRENGTH("Сила", 1.0),
    HEALTH_FOR_STRENGTH("Здоровье за ед. Силы", 1.0),
}

@Entity
data class MobBattleStats(@Id var id: Long = 0) {
    lateinit var arrayStats: ToMany<StockStatsProp>

    /**
     * По-умолчанию сохраняем все характеристики с 0м значением в персонажа
     */
    fun initializeAllStats() {
        arrayStats.clear()
        EnumPropsType.entries.filter { it != EnumPropsType.UNDEFINED }.forEach {
            arrayStats.add(StockStatsProp(type = it, valueP = it.baseValue))
        }
    }

    /**
     * Получение базового значение характеристики без учёта любых модификаторов
     */
    fun getStockStat(stat: EnumPropsType): StockStatsProp {
        return arrayStats.find { it.type == stat }!!
    }

    /**
     * Получение модифицированного значения характеристики
     */
    fun getStat(stat: EnumPropsType) : StockStatsProp {
        val resultStat = getStockStat(stat)
        return when (resultStat.type) {
            EnumPropsType.HEALTH -> {
                val propHealth = getStockStat(EnumPropsType.HEALTH)
                val propStrength = getStockStat(EnumPropsType.STRENGTH)
                val propHealthForStrength = getStockStat(EnumPropsType.HEALTH_FOR_STRENGTH)
                StockStatsProp(valueP = propHealth.getValue() + (propStrength.getWithPercent() * propHealthForStrength.getWithPercent()), percentP = propHealth.getPercent())
            }
            else -> {
                resultStat
            }
        }
    }

    override fun toString(): String {
        return "BattleStats(id=$id, arrayStats=${arrayStats.joinToString()})"
    }
}