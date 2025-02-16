package ru.descend.desarpg.repository

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import java.util.UUID

@Entity
data class InnerClasses(
    @Id var id: Long = 0,
    var name: String = UUID.randomUUID().toString(),
    var type: Int = 3
){
    var outerClass: ToOne<OuterClasses>? = null
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

    override fun toString(): String {
        return "OuterClass(id=$id, arrayStats=${arrayStats.joinToString("\n")})"
    }
}