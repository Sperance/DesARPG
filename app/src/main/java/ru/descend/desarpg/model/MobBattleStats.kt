package ru.descend.desarpg.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToMany
import ru.descend.desarpg.addPercent
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.model.converters.EnumPropsTypeConverter
import ru.descend.desarpg.to1Digits
import java.util.UUID

enum class EnumPropsType(val statName: String, val baseValue: Double) {
    UNDEFINED("", 0.0),
    HEALTH("Здоровье", 100.0),
    PHYSIC_ATTACK("Физ. атака", 5.0),
    STRENGTH("Сила", 1.0),
    HEALTH_FOR_STRENGTH("Здоровье за ед. Силы", 1.0),
}

@Entity
open class Prop(
    @Id override var id: Long = 0,
    @Convert(converter = EnumPropsTypeConverter::class, dbType = String::class)
    var type: EnumPropsType = EnumPropsType.UNDEFINED,
    var valueP: Double = 0.0,
    var percentP: Double = 0.0
) : IntEntityObjectClass {
    override fun saveToBox() {
        applicationBox.boxFor(Prop::class.java).put(this)
    }

    override fun toString(): String {
        return "Prop(id=$id, type=$type, valueP=$valueP, percentP=$percentP)"
    }
}

@Entity
data class StockStatsProp(
    var description: String = "",
): Prop() {
    @Transient
    private var currentValue = 0.0

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

    fun copy(): StockStatsProp {
        return StockStatsProp().apply {
            id = this@StockStatsProp.id
            type = this@StockStatsProp.type
            valueP = this@StockStatsProp.valueP
            percentP = this@StockStatsProp.percentP
            description = this@StockStatsProp.description
            currentValue = this@StockStatsProp.currentValue
        }
    }

    override fun saveToBox() {
        applicationBox.boxFor(StockStatsProp::class.java).put(this)
    }

    override fun toString(): String {
        return "StockStatsProp(id=$id, type='$type', value=$valueP, percent=$percentP, currentValue=$currentValue)"
    }
}

@Entity
data class MobBattleStats(
    @Id override var id: Long = 0,
    var uuid: String = UUID.randomUUID().toString()
) : IntEntityObjectClass {
    lateinit var arrayStats: ToMany<StockStatsProp>

    /**
     * По-умолчанию сохраняем все характеристики
     */
    fun initializeAllStats() {
        arrayStats.clear()
        EnumPropsType.entries.filter { it != EnumPropsType.UNDEFINED }.forEach {
            arrayStats.add(StockStatsProp().apply {type = it ; valueP = it.baseValue})
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
                StockStatsProp().apply {valueP = propHealth.getValue() + (propStrength.getWithPercent() * propHealthForStrength.getWithPercent()) ; percentP = propHealth.getPercent()}
            }
            else -> {
                resultStat
            }
        }
    }

    override fun saveToBox() {
        arrayStats.applyChangesToDb()
        applicationBox.boxFor(MobBattleStats::class.java).put(this)
    }

    override fun toString(): String {
        return "MobBattleStats(id=$id, uuid=$uuid, arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}