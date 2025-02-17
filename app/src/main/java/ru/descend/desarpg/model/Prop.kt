package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import ru.descend.desarpg.applicationBox

@Entity
open class Prop(
    @Id override var id: Long = 0,
    var type: String = "",
    var description: String = ""
) : IntEntityObjectClass {
    override fun saveToBox() {
        applicationBox.boxFor(Prop::class.java).put(this)
    }
}

@Entity
open class DoubleProp(
    var valueProp: Double = 0.0,
    var percentProp: Double = 0.0,
) : Prop() {
    override fun saveToBox() {
        applicationBox.boxFor(DoubleProp::class.java).put(this)
    }
    override fun toString(): String {
        return "DoubleProp(type=$type, valueProp=$valueProp, percentProp=$percentProp)"
    }
}

@Entity
open class BoolProp(
    var valueProp: Boolean = false
) : Prop() {
    override fun saveToBox() {
        applicationBox.boxFor(BoolProp::class.java).put(this)
    }
    override fun toString(): String {
        return "DoubleProp(type=$type, valueProp=$valueProp)"
    }
}