package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
open class Mob(@Id var id: Long = 0,
               open var name: String = "",
               var level: Byte = 1)

@Entity
class RoomMobs(
    var power: Double = 0.0
) : Mob() {
    lateinit var battleStats: ToOne<MobBattleStats>
    lateinit var systemStats: ToOne<MobSystemStats>
    lateinit var skillTreeStats: ToOne<MobSkillTreeStats>
    override fun toString(): String {
        return "RoomMobs(id=$id, name='$name')"
    }
}