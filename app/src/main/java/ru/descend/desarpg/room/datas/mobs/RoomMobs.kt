package ru.descend.desarpg.room.datas.mobs

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne
import ru.descend.desarpg.logic.BattleStats
import java.util.UUID

@Entity
data class RoomMobs(
    @Id var id: Long = 0,
    var name: String = "",
    val mobUUID: String = UUID.randomUUID().toString(),
    var mobLevel: Byte = 1
) {
    lateinit var battleStats: ToOne<BattleStats>
    override fun toString(): String {
        return "RoomMobs(id=$id, name='$name', battleStats=${battleStats.target})"
    }
}