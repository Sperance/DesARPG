package ru.descend.desarpg.repository

import io.objectbox.Box
import io.objectbox.BoxStore
import ru.descend.desarpg.log
import ru.descend.desarpg.model.EnumPropsType
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobMain
import ru.descend.desarpg.model.MobSkillTreeStats
import ru.descend.desarpg.model.MobSystemStats
import ru.descend.desarpg.model.SkillNodeEntity
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.model.SystemStatsProp

class BoxMobRepository(private val currentBox: BoxStore) {
    val battleStatsBox: Box<MobBattleStats> = currentBox.boxFor(MobBattleStats::class.java)
    private val statsPropBox: Box<StockStatsProp> = currentBox.boxFor(StockStatsProp::class.java)
    private lateinit var currentStats: MobBattleStats
    fun getStockStats() = currentStats
    fun getCurrentStats(): MobBattleStats {
        val newMobStats = MobBattleStats()
        newMobStats.arrayStats.addAll(currentStats.arrayStats.map { it.copy() })
        getCurrentSkillTreeStats().getAllNodeStats().forEach { skillStat ->
            val stat = newMobStats.getStockStat(skillStat.type)
            stat.addValue(skillStat.valueP)
            stat.addPercent(skillStat.percentP)
        }
        return newMobStats
    }

    private val systemStatsBox: Box<MobSystemStats> = currentBox.boxFor(MobSystemStats::class.java)
    private val systemPropBox: Box<SystemStatsProp> = currentBox.boxFor(SystemStatsProp::class.java)
    private lateinit var currentSystemStats: MobSystemStats
    fun getCurrentSystemStats(): MobSystemStats {
        return currentSystemStats
    }

    private val mobSkillTreeStatsBox: Box<MobSkillTreeStats> = currentBox.boxFor(MobSkillTreeStats::class.java)
    private val skillNodeEntityBox: Box<SkillNodeEntity> = currentBox.boxFor(SkillNodeEntity::class.java)
    private lateinit var currentSkillTreeStats: MobSkillTreeStats
    fun getCurrentSkillTreeStats(): MobSkillTreeStats {
        return currentSkillTreeStats
    }

    init {
//        createAndSaveMobBattleStats()
//        createAndSaveMobBattleStats_stock()
//        if (battleStatsBox.get(1) != null) {
//            log("already")
//            currentStats = battleStatsBox.get(1)
//            log("cur: $currentStats")
//            currentSystemStats = systemStatsBox.get(1)
//            currentSkillTreeStats = mobSkillTreeStatsBox.get(1)
//        } else {
//            createAndSaveMobBattleStats()
//            log("created")
//            val battleStats = MobBattleStats()
//            val systemStats = MobSystemStats()
//            val mobSkillTreeStats = MobSkillTreeStats()
//            currentBox.runInTx {
//                battleStats.initializeAllStats()
//                systemStats.initializeAllStats()
//                mobSkillTreeStats.initializeAllStats()
//
//                statsPropBox.put(battleStats.arrayStats)
//                systemPropBox.put(systemStats.arrayStats)
//                skillNodeEntityBox.put(mobSkillTreeStats.arrayStats)
//
//                systemStatsBox.put(systemStats)
//                mobSkillTreeStatsBox.put(mobSkillTreeStats)
//
//                currentStats = battleStatsBox.get(battleStatsBox.put(battleStats))
//                log("cur: $currentStats")
//                log("old: $battleStats")
//                currentSystemStats = systemStats
//                currentSkillTreeStats = mobSkillTreeStats
//            }
//        }
    }

    fun createAndSaveMobBattleStats_stock() {

        val mobBattleStatsBox = currentBox.boxFor(MobBattleStats::class.java)

        val newObject = MobBattleStats()
        newObject.arrayStats.add(StockStatsProp().apply { type = EnumPropsType.HEALTH })
        newObject.arrayStats.add(StockStatsProp().apply { type = EnumPropsType.STRENGTH })
        newObject.arrayStats.add(StockStatsProp().apply { type = EnumPropsType.PHYSIC_ATTACK })
        val obj1 = mobBattleStatsBox.get(mobBattleStatsBox.put(newObject))
        log("obj1: $obj1")

        newObject.arrayStats.add(StockStatsProp().apply { type = EnumPropsType.HEALTH_FOR_STRENGTH })

        val obj2 = mobBattleStatsBox.get(mobBattleStatsBox.put(newObject))
        log("obj2: $obj2")
    }

    fun createAndSaveMobBattleStats() {

        val boxMobMain = currentBox.boxFor(MobMain::class.java)
//        val boxOuterClass = currentBox.boxFor(OuterClasses::class.java)

        val newMob = MobMain()
        val newObject = OuterClasses()
        newObject.arrayStats.add(InnerClasses(type = 1))
        newObject.arrayStats.add(InnerClasses(type = 2))
        newObject.arrayStats.add(InnerClasses(type = 3))
        newObject.arrayStats.add(InnerClasses(type = 4))

        newMob.outerObject.target = newObject

        val obj1mob = boxMobMain.get(boxMobMain.put(newMob))
        log("obj1mob: $obj1mob")

        obj1mob.outerObject.target.arrayStats.add(InnerClasses(type = 5))
        obj1mob.outerObject.target.arrayStats.applyChangesToDb()

        val obj2mob = boxMobMain.get(boxMobMain.put(obj1mob))
        log("obj2mob: $obj2mob")
    }

    fun saveToDB() {
        currentBox.runInTx {
            statsPropBox.putBatched(currentStats.arrayStats, currentStats.arrayStats.size)
            battleStatsBox.put(currentStats)

            systemPropBox.putBatched(currentSystemStats.arrayStats, currentSystemStats.arrayStats.size)
            systemStatsBox.put(currentSystemStats)

            skillNodeEntityBox.putBatched(currentSkillTreeStats.arrayStats, currentSkillTreeStats.arrayStats.size)
            mobSkillTreeStatsBox.put(currentSkillTreeStats)
        }
    }

    fun getBox() = currentBox

    fun getAllBoxes() : ArrayList<Box<*>> {
        val list = ArrayList<Box<*>>()
        BoxMobRepository::class.java.declaredFields.forEach {
            it.isAccessible = true
            val obj = it.get(this)
            if (obj is Box<*>) list.add(obj)
            it.isAccessible = false
        }
        return list
    }
}