package ru.descend.desarpg.model

import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.log

// Абстрактный базовый класс для всех сущностей
abstract class AbsEntityBase<T>(
    val box: Box<T>,
    open var id: Long = 0,
) {
    open fun targetLinkToOne(): ToOne<*>? = null
    open fun saveToBox() {
        val result = box.put(this as T)
        log("[${box.entityClass.simpleName}][saveToBox][$result] : $this")
    }
    open fun removeFromBox() {
        targetLinkToOne()?.target = null
        val result = box.remove(this as T)
        log("[${box.entityClass.simpleName}][removeFromBox][$result] : $this")
    }
}

interface IntEntityObjectClass {
    var id: Long

    fun saveToBox()
}