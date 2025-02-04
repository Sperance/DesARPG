package ru.descend.desarpg.logic

import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToMany
import org.junit.Test
import ru.descend.desarpg.addPercent
import ru.descend.desarpg.logic.converters.EnumPropsTypeConverter
import ru.descend.desarpg.to1Digits
import kotlin.reflect.full.primaryConstructor

enum class EnumPropsType(statName: String) {
    UNDEFINED(""),
    HEALTH("Здоровье"),
    PHYSIC_ATTACK("Физ. атака"),
    STRENGTH("Сила"),
    HEALTH_FOR_STRENGTH("Здоровье за ед. Силы"),
}

@Entity
data class StockStatsProp(
    @Id var id: Long = 0,
    var name: String = "",
    @Convert(converter = EnumPropsTypeConverter::class, dbType = String::class)
    var type: EnumPropsType = EnumPropsType.UNDEFINED,
    var description: String = "",
    var valueP: Double = 0.0,
    var percentP: Double = 0.0
) {
    @Transient private var currentValue = 0.0

    fun getWithPercent() = getValue().addPercent(getPercent())

    fun getValue() = valueP
    fun setValue(newValue: Number) { valueP = newValue.toDouble().to1Digits() }
    fun removeValue(newValue: Number) { valueP = (valueP - newValue.toDouble()).to1Digits() }
    fun addValue(newValue: Number) { valueP = (valueP + newValue.toDouble()).to1Digits() }

    fun getPercent() = percentP
    fun setPercent(newValue: Number) { percentP = newValue.toDouble().to1Digits() }
    fun removePercent(newValue: Number) { percentP = (percentP - newValue.toDouble()).to1Digits() }
    fun addPercent(newValue: Number) { percentP = (percentP + newValue.toDouble()).to1Digits() }

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

    override fun toString(): String {
        return "StockStatsProp(id=$id, name='$name', type='$type', value=$valueP, percent=$percentP, currentValue=$currentValue)"
    }
}

//
//@Entity class StockStatHealth : StockStatsProp("Health") {
//    override fun getValue(): Double {
//        if (stats == null) return super.getValue()
//        return super.getValue() + (stats!!.strength.getCurrentForGlobalStats() * stats!!.healthForStrength.getCurrentForGlobalStats()).to1Digits()
//    }
//}
//@Entity class StockStatAttack : StockStatsProp("Damage") {
//    override fun getValue(): Double {
//        if (stats == null) return super.getValue()
//        return super.getValue() + (stats!!.strength.getCurrentForGlobalStats() * stats!!.attackForStrength.getCurrentForGlobalStats()).to1Digits()
//    }
//}
//@Entity class StockStatStrength : StockStatsProp("Strength")
//@Entity class StockStatHealthForStrength : StockStatsProp("HealthForStrength")
//@Entity class StockStatAttackForStrength : StockStatsProp("AttackForStrength")

class TestBattleStat {
    @Test
    fun test_catch_bs() {
        val stats = BattleStats()
        stats.arrayStats.add(StockStatsProp(name = "Здоровье", type = EnumPropsType.HEALTH, valueP = 100.0, percentP = 10.0))
        stats.arrayStats.forEach {
            println("STAT: $it")
        }
    }
}

@Entity
data class BattleStats(
    @Id var id: Long = 0
) {
    lateinit var arrayStats: ToMany<StockStatsProp>

    fun getStat(stat: EnumPropsType) : StockStatsProp? {
        return arrayStats.find { it.type == stat }
    }

    fun save(boxStore: BoxStore) {
        val battleStatsBox = boxStore.boxFor(BattleStats::class.java)
        val statsPropBox = boxStore.boxFor(StockStatsProp::class.java)
        boxStore.runInTx {
            arrayStats.forEach { stat -> statsPropBox.put(stat) }
            battleStatsBox.put(this)
        }
    }

    /* MAIN METHODS */
    fun getCalcHealth() : StockStatsProp {
        val propHealth = getStat(EnumPropsType.HEALTH)!!
        val propStrength = getStat(EnumPropsType.STRENGTH)!!
        return StockStatsProp(name = "Здоровье", valueP = propHealth.getWithPercent() + propStrength.getWithPercent(), percentP = propHealth.getPercent())
    }
    fun getCalcStrength() : StockStatsProp {
        val propStrength = getStat(EnumPropsType.STRENGTH)!!
        return propStrength
    }

    override fun toString(): String {
        return "BattleStats(id=$id, arrayStats=${arrayStats.joinToString()})"
    }
}

class StatModObj {
    private val listParams = ArrayList<StatMods>()

    fun getList() = listParams

    fun addParam(param: StatMods) {
        if (listParams.find { it.hash == param.hash } == null) listParams.add(param)
    }
    fun removeParam(hash: Int) {
        listParams.removeIf { it.hash == hash }
    }
    fun clearParams() {
        listParams.clear()
    }
}
data class StatMods (
    val hash: Int,
    val addValue: Double = 0.0,
    val addPercent: Double = 0.0,
    val addBattle: Double = 0.0
)
