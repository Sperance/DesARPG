package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import ru.descend.desarpg.repository.OuterClasses
import java.util.UUID

@Entity
data class MobMain(
    @Id var id: Long = 0,
    var name: String = UUID.randomUUID().toString(),
    var description: String = ""
){
    lateinit var outerObject: ToOne<OuterClasses>
    override fun toString(): String {
        return "MobMain(id=$id, name='$name', description='$description', outerObject=${outerObject.target})"
    }
}