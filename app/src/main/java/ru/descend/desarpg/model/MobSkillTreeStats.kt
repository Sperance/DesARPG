package ru.descend.desarpg.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import ru.descend.desarpg.R

@Entity
data class SkillNodeEntity(
    @Id override var id: Long = 0,
    val name: String,
    val code: Int,
    val positionX: Float,
    val positionY: Float,
    val iconInt: Int? = null,
    var connectionCode: Int? = null,
    var isActivated: Boolean = false,
    var level: Int = 1,
    var maxLevel: Int = 1,
    var bonusForLevel: Int = 20
) : AbsEntityBase<SkillNodeEntity> {
    lateinit var arrayStats: ToMany<StockStatsProp>

    fun getCurrentBonusForLevel(): Int {
        if (level <= 1 || maxLevel <= 1 || bonusForLevel <= 0) return 0
        return (level - 1) * bonusForLevel
    }

    override fun getClassObj(): Class<SkillNodeEntity> = SkillNodeEntity::class.java
    override fun toString(): String {
        return "SkillNodeEntity(id=$id, name='$name', code=$code, iconInt=$iconInt, connectionCode=$connectionCode, isActivated=$isActivated, arrayStats=${arrayStats.joinToString()})"
    }
}

fun createLargeSkillTree() : ArrayList<SkillNodeEntity> {
    val nodes = ArrayList<SkillNodeEntity>()

    // Центральный узел
    val centerNode = SkillNodeEntity(
        code = 1,
        name = "Center Node",
        positionX = 0f,
        positionY = 0f,
        iconInt = R.drawable.ic_android_black_24dp,
        connectionCode = null,
        isActivated = true,
        maxLevel = 5
    )
    centerNode.arrayStats.add(StockStatsProp().apply { type = EnumPropsType.HEALTH.name ; valueProp = 50.0 ; percentProp = 10.0 })
    centerNode.arrayStats.add(StockStatsProp().apply { type = EnumPropsType.STRENGTH.name ; percentProp = 10.0 })
    nodes.add(centerNode)

    // Узлы первого уровня (6 узлов вокруг центрального)
    val firstLevelNodes = listOf(
        SkillNodeEntity(code = 2, name = "Node 2", positionX = -500f, positionY = 0f, iconInt = null, connectionCode = 1, isActivated = false),
        SkillNodeEntity(code = 3, name = "Node 3", positionX = 500f, positionY = 0f, iconInt = null, connectionCode = 1, isActivated = false),
        SkillNodeEntity(code = 4, name = "Node 4", positionX = 0f, positionY = -500f, iconInt = null, connectionCode = 1, isActivated = false),
        SkillNodeEntity(code = 5, name = "Node 5", positionX = 0f, positionY = 500f, iconInt = null, connectionCode = 1, isActivated = false),
        SkillNodeEntity(code = 6, name = "Node 6", positionX = -353.55f, positionY = -353.55f, iconInt = null, connectionCode = 1, isActivated = false).apply {
            arrayStats.add(StockStatsProp().apply { type = EnumPropsType.STRENGTH.name ; valueProp = 3.0 })
        },
        SkillNodeEntity(code = 7, name = "Node 7", positionX = 353.55f, positionY = -353.55f, iconInt = null, connectionCode = 1, isActivated = false)
    )
    nodes.addAll(firstLevelNodes)

    // Узлы второго уровня
    val secondLevelNodes = listOf(
        SkillNodeEntity(code = 8, name = "Node 8", positionX = -750f, positionY = 0f, iconInt = null, connectionCode = 2, isActivated = false),
        SkillNodeEntity(code = 9, name = "Node 9", positionX = -625f, positionY = -250f, iconInt = null, connectionCode = 2, isActivated = false),
        SkillNodeEntity(code = 10, name = "Node 10", positionX = -625f, positionY = 250f, iconInt = null, connectionCode = 2, isActivated = false),
        SkillNodeEntity(code = 11, name = "Node 11", positionX = 750f, positionY = 0f, iconInt = null, connectionCode = 3, isActivated = false),
        SkillNodeEntity(code = 12, name = "Node 12", positionX = 625f, positionY = -250f, iconInt = null, connectionCode = 3, isActivated = false),
        SkillNodeEntity(code = 13, name = "Node 13", positionX = 625f, positionY = 250f, iconInt = null, connectionCode = 3, isActivated = false),
        SkillNodeEntity(code = 14, name = "Node 14", positionX = 0f, positionY = -750f, iconInt = null, connectionCode = 4, isActivated = false),
        SkillNodeEntity(code = 15, name = "Node 15", positionX = -250f, positionY = -625f, iconInt = null, connectionCode = 4, isActivated = false),
        SkillNodeEntity(code = 16, name = "Node 16", positionX = 250f, positionY = -625f, iconInt = null, connectionCode = 4, isActivated = false),
        SkillNodeEntity(code = 17, name = "Node 17", positionX = 0f, positionY = 750f, iconInt = null, connectionCode = 5, isActivated = false),
        SkillNodeEntity(code = 18, name = "Node 18", positionX = -250f, positionY = 625f, iconInt = null, connectionCode = 5, isActivated = false),
        SkillNodeEntity(code = 19, name = "Node 19", positionX = 250f, positionY = 625f, iconInt = null, connectionCode = 5, isActivated = false),
        SkillNodeEntity(code = 20, name = "Node 20", positionX = -500f, positionY = -500f, iconInt = null, connectionCode = 6, isActivated = false),
        SkillNodeEntity(code = 21, name = "Node 21", positionX = -500f, positionY = 500f, iconInt = null, connectionCode = 1, isActivated = false),
        SkillNodeEntity(code = 22, name = "Node 22", positionX = 500f, positionY = -500f, iconInt = null, connectionCode = 7, isActivated = false),
        SkillNodeEntity(code = 23, name = "Node 23", positionX = 500f, positionY = 500f, iconInt = null, connectionCode = 1, isActivated = false)
    )
    nodes.addAll(secondLevelNodes)

    // Узлы третьего уровня
    val thirdLevelNodes = listOf(
        SkillNodeEntity(code = 24, name = "Node 24", positionX = -900f, positionY = 0f, iconInt = null, connectionCode = 8, isActivated = false),
        SkillNodeEntity(code = 25, name = "Node 25", positionX = -775f, positionY = -250f, iconInt = null, connectionCode = 9, isActivated = false),
        SkillNodeEntity(code = 26, name = "Node 26", positionX = -775f, positionY = 250f, iconInt = null, connectionCode = 10, isActivated = false),
        SkillNodeEntity(code = 27, name = "Node 27", positionX = 900f, positionY = 0f, iconInt = null, connectionCode = 11, isActivated = false),
        SkillNodeEntity(code = 28, name = "Node 28", positionX = 775f, positionY = -250f, iconInt = null, connectionCode = 12, isActivated = false),
        SkillNodeEntity(code = 29, name = "Node 29", positionX = 775f, positionY = 250f, iconInt = null, connectionCode = 13, isActivated = false),
        SkillNodeEntity(code = 30, name = "Node 30", positionX = 0f, positionY = -900f, iconInt = null, connectionCode = 14, isActivated = false),
        SkillNodeEntity(code = 31, name = "Node 31", positionX = -250f, positionY = -775f, iconInt = null, connectionCode = 15, isActivated = false),
        SkillNodeEntity(code = 32, name = "Node 32", positionX = 250f, positionY = -775f, iconInt = null, connectionCode = 16, isActivated = false),
        SkillNodeEntity(code = 33, name = "Node 33", positionX = 0f, positionY = 900f, iconInt = null, connectionCode = 17, isActivated = false),
        SkillNodeEntity(code = 34, name = "Node 34", positionX = -250f, positionY = 775f, iconInt = null, connectionCode = 18, isActivated = false),
        SkillNodeEntity(code = 35, name = "Node 35", positionX = 250f, positionY = 775f, iconInt = null, connectionCode = 19, isActivated = false),
        SkillNodeEntity(code = 36, name = "Node 36", positionX = -650f, positionY = -650f, iconInt = null, connectionCode = 20, isActivated = false),
        SkillNodeEntity(code = 37, name = "Node 37", positionX = -650f, positionY = 650f, iconInt = null, connectionCode = 21, isActivated = false),
        SkillNodeEntity(code = 38, name = "Node 38", positionX = 650f, positionY = -650f, iconInt = null, connectionCode = 22, isActivated = false),
        SkillNodeEntity(code = 39, name = "Node 39", positionX = 650f, positionY = 650f, iconInt = null, connectionCode = 23, isActivated = false)
    )
    nodes.addAll(thirdLevelNodes)

    // Узлы четвертого уровня
    val fourthLevelNodes = listOf(
        SkillNodeEntity(code = 40, name = "Node 40", positionX = -1100f, positionY = 0f, iconInt = null, connectionCode = 24, isActivated = false),
        SkillNodeEntity(code = 41, name = "Node 41", positionX = -975f, positionY = -250f, iconInt = null, connectionCode = 25, isActivated = false),
        SkillNodeEntity(code = 42, name = "Node 42", positionX = -975f, positionY = 250f, iconInt = null, connectionCode = 26, isActivated = false),
        SkillNodeEntity(code = 43, name = "Node 43", positionX = 1100f, positionY = 0f, iconInt = null, connectionCode = 27, isActivated = false),
        SkillNodeEntity(code = 44, name = "Node 44", positionX = 975f, positionY = -250f, iconInt = null, connectionCode = 28, isActivated = false),
        SkillNodeEntity(code = 45, name = "Node 45", positionX = 975f, positionY = 250f, iconInt = null, connectionCode = 29, isActivated = false),
        SkillNodeEntity(code = 46, name = "Node 46", positionX = 0f, positionY = -1100f, iconInt = null, connectionCode = 30, isActivated = false),
        SkillNodeEntity(code = 47, name = "Node 47", positionX = -250f, positionY = -975f, iconInt = null, connectionCode = 31, isActivated = false),
        SkillNodeEntity(code = 48, name = "Node 48", positionX = 250f, positionY = -975f, iconInt = null, connectionCode = 32, isActivated = false),
        SkillNodeEntity(code = 49, name = "Node 49", positionX = 0f, positionY = 1100f, iconInt = null, connectionCode = 33, isActivated = false),
        SkillNodeEntity(code = 50, name = "Node 50", positionX = -250f, positionY = 975f, iconInt = null, connectionCode = 34, isActivated = false),
        SkillNodeEntity(code = 51, name = "Node 51", positionX = 250f, positionY = 975f, iconInt = null, connectionCode = 35, isActivated = false),
        SkillNodeEntity(code = 52, name = "Node 52", positionX = -850f, positionY = -850f, iconInt = null, connectionCode = 36, isActivated = false),
        SkillNodeEntity(code = 53, name = "Node 53", positionX = -850f, positionY = 850f, iconInt = null, connectionCode = 37, isActivated = false),
        SkillNodeEntity(code = 54, name = "Node 54", positionX = 850f, positionY = -850f, iconInt = null, connectionCode = 38, isActivated = false),
        SkillNodeEntity(code = 55, name = "Node 55", positionX = 850f, positionY = 850f, iconInt = null, connectionCode = 39, isActivated = false)
    )
    nodes.addAll(fourthLevelNodes)

    // Узлы пятого уровня
    val fifthLevelNodes = listOf(
        SkillNodeEntity(code = 56, name = "Node 56", positionX = -1300f, positionY = 0f, iconInt = null, connectionCode = 40, isActivated = false),
        SkillNodeEntity(code = 57, name = "Node 57", positionX = -1175f, positionY = -250f, iconInt = null, connectionCode = 41, isActivated = false),
        SkillNodeEntity(code = 58, name = "Node 58", positionX = -1175f, positionY = 250f, iconInt = null, connectionCode = 42, isActivated = false),
        SkillNodeEntity(code = 59, name = "Node 59", positionX = 1300f, positionY = 0f, iconInt = null, connectionCode = 43, isActivated = false),
        SkillNodeEntity(code = 60, name = "Node 60", positionX = 1175f, positionY = -250f, iconInt = null, connectionCode = 44, isActivated = false)
    )
    nodes.addAll(fifthLevelNodes)

    return nodes
}

@Entity
data class MobSkillTreeStats(@Id override var id: Long = 0): AbsEntityBase<MobSkillTreeStats> {
    lateinit var arrayStats: ToMany<SkillNodeEntity>

    fun initializeAllStats() {
        arrayStats.clear()
        arrayStats.addAll(createLargeSkillTree())
    }

    fun getActiveNodes() : ArrayList<SkillNodeEntity> {
        val result = ArrayList<SkillNodeEntity>()
        result.addAll(arrayStats.filter { it.isActivated && !it.arrayStats.isEmpty() })
        return result
    }

    override fun getClassObj(): Class<MobSkillTreeStats> = MobSkillTreeStats::class.java
    override fun saveToBox() {
        arrayStats.forEach {
            it.saveToBox()
        }
        super.saveToBox()
    }
    override fun toString(): String {
        return "MobSkillTreeStats(id=$id, arrayStats=\n${arrayStats.joinToString("\n")})"
    }
}