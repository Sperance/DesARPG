package ru.descend.desarpg.repository

import io.objectbox.BoxStore
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import java.util.UUID

@Entity
open class SubInnerClass(
    @Id open var id: Long = 0,
    var name: String = UUID.randomUUID().toString(),
) {
    override fun toString(): String {
        return "SubInnerClass(id=$id, name='$name')"
    }
}

@Entity
data class InnerClasses(
//    @Id override var id: Long = 0,
    var type: Int = 3
): SubInnerClass (){
    var outerClass: ToOne<OuterClasses>? = null

    fun saveToDB(box: BoxStore) {
        box.boxFor(InnerClasses::class.java).put(this)
    }

    override fun toString(): String {
        return "InnerClass(id=$id, name='$name', type=$type)"
    }
}

@Entity
data class OuterClasses(
    @Id var id: Long = 0,
){
    @Backlink(to = "outerClass")
    lateinit var arrayStats: ToMany<InnerClasses>

    fun saveAllToBox() {
        arrayStats.forEach {
            applicationBox.boxFor(InnerClasses::class.java).put(it)
        }
    }

    override fun toString(): String {
        return "OuterClass(id=$id, arrayStats=${arrayStats.joinToString("\n")})"
    }
}