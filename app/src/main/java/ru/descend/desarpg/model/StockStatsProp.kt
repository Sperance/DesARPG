package ru.descend.desarpg.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import ru.descend.desarpg.addPercent
import ru.descend.desarpg.model.converters.EnumPropsTypeConverter
import ru.descend.desarpg.to1Digits

@Entity
data class StockStatsProp(
    @Id var id: Long = 0,
    @Convert(converter = EnumPropsTypeConverter::class, dbType = String::class)
    var type: EnumPropsType = EnumPropsType.UNDEFINED,
    var description: String = "",
    var valueP: Double = 0.0,
    var percentP: Double = 0.0
) {
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

    override fun toString(): String {
        return "StockStatsProp(id=$id, type='$type', value=$valueP, percent=$percentP, currentValue=$currentValue)"
    }
}