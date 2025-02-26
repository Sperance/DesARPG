package ru.descend.desarpg.model

import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.log

interface AbsEntityBase<T : AbsEntityBase<T>> {
    fun getClassObj(): Class<T>
    var id: Long

    fun targetLinkToOne(): ToOne<*>? = null
    fun saveToBox() {
        val result = applicationBox.boxFor(getClassObj()).put(this as T)
        log("[${getClassObj().simpleName}][saveToBox][$result] : $this")
    }
    fun removeFromBox() {
        targetLinkToOne()?.target = null
        val result = applicationBox.boxFor(getClassObj()).remove(this as T)
        log("[${getClassObj().simpleName}][removeFromBox][$result] : $this")
    }
}