package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import java.util.UUID

@Entity
open class Mob(@Id var id: Long = 0,
               open var name: String = "",
               var level: Byte = 1)

@Entity
class RoomMobs(
    name: String = "",
    var power: Double = 0.0
) : Mob() {
    lateinit var battleStats: ToOne<MobBattleStats>
    lateinit var systemStats: ToOne<MobSystemStats>
    override fun toString(): String {
        return "RoomMobs(id=$id, name='$name', battleStats=${battleStats.target}, systemStats=${systemStats.target})"
    }
}