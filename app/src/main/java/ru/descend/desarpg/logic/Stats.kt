package ru.descend.desarpg.logic

import kotlinx.serialization.Serializable
import ru.descend.desarpg.addPercent
import ru.descend.desarpg.to0Digits
import ru.descend.desarpg.to1Digits

@Serializable
abstract class Prop <T> {
    abstract val name: String
    abstract val description: String
    abstract var stockValue: T
    override fun toString(): String {
        return "Prop(name=$name stockValue=$stockValue)"
    }
}

@Serializable
open class PropertyValue(
    override val name: String,
    override var stockValue: Double = 0.0,
    override val description: String = "",
    var stockPercent: Double = 0.0
) : Prop<Double>() {

    private var battleValue = get()

    fun initForBattle() {
        setBattle(get())
    }

    fun getBattle() = battleValue
    fun setBattle(value: Double) {
        val beforeStock = battleValue
        battleValue = value.to1Digits()
        if (battleValue > get()) battleValue = get().to1Digits()
        if (battleValue <= 0) battleValue = 0.0
        if (beforeStock != battleValue) arrayListeners.invoke()
    }
    fun addBattle(value: Double) {
        val beforeStock = battleValue
        battleValue = (battleValue + value).to1Digits()
        if (battleValue > get()) battleValue = get().to1Digits()
        if (beforeStock != battleValue) arrayListeners.invoke()
    }
    fun removeBattle(value: Double) {
        val beforeStock = battleValue
        battleValue = (battleValue - value).to1Digits()
        if (battleValue <= 0) battleValue = 0.0
        if (beforeStock != battleValue) arrayListeners.invoke()
    }

    fun get() = stockValue.addPercent(stockPercent).to1Digits()
    fun set(value: Double) { stockValue = value.to1Digits() }
    fun remove(value: Double) { stockValue = (stockValue - value).to1Digits() }
    fun add(value: Double) { stockValue = (stockValue + value).to1Digits() }

    override fun toString(): String {
        return "(name='$name', stockValue=$stockValue, stockPercent=$stockPercent, battleValue=$battleValue, get=${get()})"
    }

    val arrayListeners = ItemListener("Изменение")
}

@Serializable
open class PropertyBlob(
    override val name: String,
    override var stockValue: Boolean,
    override val description: String = ""
) : Prop<Boolean>() {
    fun get() = stockValue
    fun set(value: Boolean) { stockValue = value }
}

@Serializable
open class BattleStats {
    val Health = SealedStats.Health()
    val AttackPhysic = SealedStats.AttackPhysic()
    val Strength = SealedStats.Strength()

    fun initForBattle() {
        Health.initForBattle()
        AttackPhysic.initForBattle()
        Strength.initForBattle()
    }
}

@Serializable
open class BaseStats {
    val experience = PropertyValue("Опыт")
}

@Serializable
open class BoolStats {
    val isCanAttack = PropertyBlob("Может атаковать", true)
    val isAlive = PropertyBlob("Живой", true)
}

sealed class SealedStats {
    @Serializable
    class Health : PropertyValue("Здоровье", 50.0)
    @Serializable
    class AttackPhysic : PropertyValue("Физ. атака")
    @Serializable
    class Strength : PropertyValue("Сила", 1.0)
}