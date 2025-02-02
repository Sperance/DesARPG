package ru.descend.desarpg.logic

import ru.descend.desarpg.addPercent
import ru.descend.desarpg.to1Digits

abstract class Prop {
    abstract val name: String
    var description: String = ""
    private var value: Double = 0.0
    private var percent: Double = 0.0

    fun getWithPercent() = get().addPercent(getPercent())

    open fun get() = value
    fun set(newValue: Number) { value = newValue.toDouble().to1Digits() }
    fun remove(newValue: Number) { value = (value - newValue.toDouble()).to1Digits() }
    fun add(newValue: Number) { value = (value + newValue.toDouble()).to1Digits() }

    fun getPercent() = percent
    fun setPercent(newValue: Number) { percent = newValue.toDouble().to1Digits() }
    fun removePercent(newValue: Number) { percent = (percent - newValue.toDouble()).to1Digits() }
    fun addPercent(newValue: Number) { percent = (percent + newValue.toDouble()).to1Digits() }
}

abstract class StockStatsProp(override var name: String) : Prop() {
    @Transient private var currentValue = 0.0
    @Transient lateinit var stats: BattleStats
    fun prepareInit() {
        currentValue = get().addPercent(getPercent()).to1Digits()
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
        return "StockStatsProp(name='$name', get=${get()}, percent=${getPercent()}, currentValue=$currentValue)"
    }
}

sealed class StockStats {
    class StockStatHealth : StockStatsProp("Health") {
        override fun get(): Double {
            return super.get() + (stats.strength.getCurrentForGlobalStats() * stats.healthForStrength.getCurrentForGlobalStats()).to1Digits()
        }
    }
    class StockStatAttack : StockStatsProp("Damage") {
        override fun get(): Double {
            return super.get() + (stats.strength.getCurrentForGlobalStats() * stats.attackForStrength.getCurrentForGlobalStats()).to1Digits()
        }
    }
    class StockStatStrength : StockStatsProp("Strength")
    class StockStatHealthForStrength : StockStatsProp("HealthForStrength")
    class StockStatAttackForStrength : StockStatsProp("AttackForStrength")
}

class BattleStats {
    var health = StockStats.StockStatHealth()
    var attackPhysic = StockStats.StockStatAttack()
    var strength = StockStats.StockStatStrength()
    var healthForStrength = StockStats.StockStatHealthForStrength()
    var attackForStrength = StockStats.StockStatAttackForStrength()

    init {
        health.stats = this
        attackPhysic.stats = this
        strength.stats = this
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
