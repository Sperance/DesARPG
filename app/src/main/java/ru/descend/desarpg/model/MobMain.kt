package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.repository.OuterClasses
import java.util.UUID

@Entity
data class MobMain(
    @Id override var id: Long = 0,
    var name: String = UUID.randomUUID().toString(),
    var description: String = ""
): IntEntityObjectClass {
    lateinit var mobBattleStats: ToOne<MobBattleStats>
    lateinit var mobSystemStats: ToOne<MobSystemStats>
    lateinit var mobSkillTreeStats: ToOne<MobSkillTreeStats>
    override fun saveToBox() {
        applicationBox.boxFor(MobMain::class.java).put(this)
    }
    override fun toString(): String {
        return "MobMain(id=$id, name='$name', description='$description')"
    }
}