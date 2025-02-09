package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SkillNodeEntity(
    @Id var id: Long = 0,
    val name: String,
    val positionX: Float,
    val positionY: Float,
    var isActivated: Boolean = false
)