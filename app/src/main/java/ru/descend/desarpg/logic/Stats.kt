package ru.descend.desarpg.logic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.descend.desarpg.addPercent
import ru.descend.desarpg.to1Digits

@Serializable
abstract class Prop {
    abstract val name: String
    var description: String = ""
    private var value: Double = 0.0
    private var percent: Double = 0.0

    fun getWithPercent() = get().addPercent(getPercent())

    fun get() = value
    fun set(newValue: Number) { value = newValue.toDouble().to1Digits() }
    fun remove(newValue: Number) { value = (value - newValue.toDouble()).to1Digits() }
    fun add(newValue: Number) { value = (value + newValue.toDouble()).to1Digits() }

    fun getPercent() = percent
    fun setPercent(newValue: Number) { percent = newValue.toDouble().to1Digits() }
    fun removePercent(newValue: Number) { percent = (percent - newValue.toDouble()).to1Digits() }
    fun addPercent(newValue: Number) { percent = (percent + newValue.toDouble()).to1Digits() }
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
) {
    override fun toString(): String {
        return "(hash='$hash', value=$addValue, percent=$addPercent, battle=$addBattle)"
    }
}

@Serializable
open class PropertyValue(
    override val name: String
) : Prop(), IntBattleChanges {
    lateinit var mob: Mob

    @Transient private var currentValue = 0.0

    fun prepareInit() {
        currentValue = get().addPercent(getPercent())
    }

    private fun getCurrentForGlobalStats(): Double {
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
        return "(name='$name', value=${get()}, percent=${getPercent()}, currentValue=${getCurrent()}, global=${getCurrentForGlobalStats()})"
    }
}

@Serializable
open class PropertyBlob(
    val name: String,
    var value: Boolean = false,
    val description: String = ""
)

@Serializable
class BattleStats(
    @SerialName("hlt") var health: StatHealth = StatHealth(),
    @SerialName("aPh") var attackPhysic: StatAttackPhysic = StatAttackPhysic(),
    @SerialName("str") var strength: StatStrength = StatStrength(),
)

//@Serializable
//open class BoolStats {
//    val isCanAttack = PropertyBlob("Может атаковать", true)
//    val isAlive = PropertyBlob("Живой", true)
//}
