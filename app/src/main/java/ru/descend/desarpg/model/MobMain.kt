package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import java.util.UUID

@Entity
data class MobMain(
    @Id override var id: Long = 0,
    var name: String = UUID.randomUUID().toString(),
    var description: String = ""
): AbsEntityBase<MobMain> {
    lateinit var mobBattleStats: ToOne<MobBattleStats>
    lateinit var mobSystemStats: ToOne<MobSystemStats>
    lateinit var mobSkillTreeStats: ToOne<MobSkillTreeStats>
    lateinit var mobInventory: ToOne<MobInventory>
    lateinit var mobWorks: ToOne<MobWorkStats>

    override fun getClassObj(): Class<MobMain> = MobMain::class.java
    override fun toString(): String {
        return "MobMain(id=$id, name='$name', description='$description')"
    }
}