package ru.descend.desarpg.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.addPercent
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.to1Digits
import java.util.UUID

enum class EnumPropsType(val statName: String, val baseValue: Double) {
    UNDEFINED("", 0.0),
    HEALTH("Здоровье", 100.0),
    PHYSIC_ATTACK("Физ. атака", 5.0),
    STRENGTH("Сила", 1.0),
    HEALTH_FOR_STRENGTH("Здоровье за ед. Силы", 1.0);

    companion object {
        fun getFromName(statName: String) : EnumPropsType? {
            return entries.find { it.name == statName }
        }
    }
}

@Entity
open class StockStatsProp(
    @Id override var id: Long = 0,
    var type: String = "",
    var description: String = "",
    var valueProp: Double = 0.0,
    var percentProp: Double = 0.0,
): AbsEntityBase<StockStatsProp> {
    var mobBattleStats: ToOne<MobBattleStats>? = null

    @Transient
    private var currentValue = 0.0

    fun getWithPercent() = getValue().addPercent(getPercent())

    fun getValue() = valueProp
    fun setValue(newValue: Number) : StockStatsProp { valueProp = newValue.toDouble().to1Digits() ; return this }
    fun removeValue(newValue: Number) : StockStatsProp { valueProp = (valueProp - newValue.toDouble()).to1Digits() ; return this }
    fun addValue(newValue: Number) : StockStatsProp { valueProp = (valueProp + newValue.toDouble()).to1Digits() ; return this }

    fun getPercent() = percentProp
    fun setPercent(newValue: Number) : StockStatsProp { percentProp = newValue.toDouble().to1Digits() ; return this }
    fun removePercent(newValue: Number) : StockStatsProp { percentProp = (percentProp - newValue.toDouble()).to1Digits() ; return this }
    fun addPercent(newValue: Number) : StockStatsProp { percentProp = (percentProp + newValue.toDouble()).to1Digits() ; return this }

    fun prepareInit() {
        currentValue = getValue().addPercent(getPercent()).to1Digits()
    }

    fun getCurrentForGlobalStats(): Double {
        prepareInit()
        return getCurrent()
    }

    fun getCurrent(): Double {
        return currentValue
    }

    fun setCurrent(newValue: Number) { currentValue = newValue.toDouble().to1Digits() }
    fun removeCurrent(newValue: Number) { currentValue = (currentValue - newValue.toDouble()).to1Digits() }
    fun addCurrent(newValue: Number) { currentValue = (currentValue + newValue.toDouble()).to1Digits() }

    fun copy(): StockStatsProp {
        return StockStatsProp().apply {
            id = this@StockStatsProp.id
            type = this@StockStatsProp.type
            valueProp = this@StockStatsProp.valueProp
            percentProp = this@StockStatsProp.percentProp
            description = this@StockStatsProp.description
            currentValue = this@StockStatsProp.currentValue
        }
    }

    override fun getClassObj(): Class<StockStatsProp> = StockStatsProp::class.java
    override fun targetLinkToOne(): ToOne<*>? = mobBattleStats
    override fun toString(): String {
        return "StockStatsProp(id=$id, type='$type', valueProp=$valueProp, percentProp=$percentProp, mobBattleStats=${mobBattleStats?.target?.id})"
    }
}

@Entity
data class MobBattleStats(
    @Id override var id: Long = 0,
    var uuid: String = UUID.randomUUID().toString()
): AbsEntityBase<MobBattleStats> {
    @Backlink(to = "mobBattleStats")
    lateinit var arrayStats: ToMany<StockStatsProp>

    fun initializeAllStats() {
        arrayStats.clear()
        EnumPropsType.entries.filter { it != EnumPropsType.UNDEFINED }.forEach {
            arrayStats.add(StockStatsProp().apply {type = it.name ; valueProp = it.baseValue})
        }
    }

    fun getStockStat(stat: EnumPropsType): StockStatsProp {
        return arrayStats.find { it.type == stat.name }!!
    }

    fun getStockStat(stat: String): StockStatsProp {
        return arrayStats.find { it.type == stat }!!
    }

    fun getStat(stat: EnumPropsType) : StockStatsProp {
        val resultStat = getStockStat(stat)
        return when (resultStat.type) {
            EnumPropsType.HEALTH.name -> {
                val propHealth = getStockStat(EnumPropsType.HEALTH)
                val propStrength = getStockStat(EnumPropsType.STRENGTH)
                val propHealthForStrength = getStockStat(EnumPropsType.HEALTH_FOR_STRENGTH)
                StockStatsProp().apply {valueProp = propHealth.getValue() + (propStrength.getWithPercent() * propHealthForStrength.getWithPercent()) ; percentProp = propHealth.getPercent()}
            }
            else -> {
                resultStat
            }
        }
    }

    override fun getClassObj(): Class<MobBattleStats> = MobBattleStats::class.java
    override fun saveToBox() {
        arrayStats.forEach { it.saveToBox() }
        super.saveToBox()
    }
    override fun toString(): String {
        return "MobBattleStats(id=$id, uuid=$uuid, arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}