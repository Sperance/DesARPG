package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import ru.descend.desarpg.R

@Entity
data class SkillNodeEntity(
    @Id var id: Long = 0,
    val name: String,
    val code: Int,
    val positionX: Float,
    val positionY: Float,
    val iconInt: Int? = null,
    val connection: Int? = null,
    var isActivated: Boolean = false
)

@Entity
data class MobSkillTreeStats(@Id var id: Long = 0) {
    lateinit var arrayStats: ToMany<SkillNodeEntity>

    fun initializeAllStats() {
        val node1 = SkillNodeEntity(code = 1, positionX = 100f, positionY = 100f, name = "Node 1", connection = 2, isActivated = true)
        val node2 = SkillNodeEntity(code = 2, positionX = 300f, positionY = 300f, name = "Node 2", connection = 3)
        val node3 = SkillNodeEntity(code = 3, positionX = 500f, positionY = 100f, name = "Node 3", iconInt = R.drawable.ic_android_black_24dp)

        arrayStats.addAll(listOf(node1, node2, node3))
    }

    override fun toString(): String {
        return "MobSkillTreeStats(id=$id, arrayStats=${arrayStats.joinToString()})"
    }
}