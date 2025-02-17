package ru.descend.desarpg.repository

import io.objectbox.Box
import io.objectbox.BoxStore
import ru.descend.desarpg.applicationBox
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

    private val mobMainBox = currentBox.boxFor(MobMain::class.java)
    private var currentMob: MobMain? = null
    fun getCurrentMob(): MobMain {
        if (mobMainBox.isEmpty || currentMob == null) {
            log("[CREATED]")

            val newMob = MobMain(name = "Player")

            val newMobBattleStats = MobBattleStats()
            newMobBattleStats.initializeAllStats()
            newMob.mobBattleStats.target = newMobBattleStats

            val newMobSystemStats = MobSystemStats()
            newMobSystemStats.initializeAllStats()
            newMob.mobSystemStats.target = newMobSystemStats

            val newMobSkillTreeStats = MobSkillTreeStats()
            newMobSkillTreeStats.initializeAllStats()
            newMob.mobSkillTreeStats.target = newMobSkillTreeStats

            currentMob = mobMainBox.get(mobMainBox.put(newMob))
            return currentMob!!
        } else {
            log("[ALREADY]")

            currentMob = mobMainBox.get(1)
            return currentMob!!
        }
    }

    private val currentStats = getCurrentMob().mobBattleStats.target
    fun getStockStats() = currentStats
    fun getCurrentStats(): MobBattleStats {
        val newMobStats = MobBattleStats()
        newMobStats.arrayStats.addAll(currentStats.arrayStats.map { it.copy() })
        getCurrentSkillTreeStats().getAllNodeStats().forEach { skillStat ->
            val stat = newMobStats.getStockStat(skillStat.type)
            stat.addValue(skillStat.valueProp)
            stat.addPercent(skillStat.percentProp)
        }
        return newMobStats
    }

    private val currentSystemStats = getCurrentMob().mobSystemStats.target
    fun getCurrentSystemStats(): MobSystemStats {
        return currentSystemStats
    }

    private val currentSkillTreeStats = getCurrentMob().mobSkillTreeStats.target
    fun getCurrentSkillTreeStats(): MobSkillTreeStats {
        return currentSkillTreeStats
    }

    fun createAndSaveMobBattleStats_stock() {

        val mobBattleStatsBox = currentBox.boxFor(MobBattleStats::class.java)

        val newObject = MobBattleStats()
        newObject.initializeAllStats()
        val obj1 = mobBattleStatsBox.get(mobBattleStatsBox.put(newObject))
        log("obj1: $obj1")

        obj1.getStockStat(EnumPropsType.HEALTH).addValue(20).saveToBox()
//        obj1.arrayStats.applyChangesToDb()

        log("obj1 mod: $obj1")

        val obj2 = mobBattleStatsBox.get(mobBattleStatsBox.put(obj1))
        log("obj2: $obj2")
    }

    fun createAndSaveMobBattleStats_stock2() {

        val mobMainBox = currentBox.boxFor(MobMain::class.java)

        val newMob = MobMain()
        val newObject = MobBattleStats()
        newObject.initializeAllStats()

        newMob.mobBattleStats.target = newObject

        val obj1 = mobMainBox.get(mobMainBox.put(newMob))
        log("obj1: $obj1")

        obj1.mobBattleStats.target.getStockStat(EnumPropsType.HEALTH).addValue(25).saveToBox()

//        obj1.getStockStat(EnumPropsType.HEALTH).addValue(20).saveToBox()
//        obj1.arrayStats.applyChangesToDb()

        log("obj1 mod: $obj1")

        val obj2 = mobMainBox.get(mobMainBox.put(obj1))
        log("obj2: $obj2")
    }

//    fun saveToDB() {
//        currentBox.runInTx {
//            statsPropBox.putBatched(currentStats.arrayStats, currentStats.arrayStats.size)
//            battleStatsBox.put(currentStats)
//
//            systemPropBox.putBatched(currentSystemStats.arrayStats, currentSystemStats.arrayStats.size)
//            systemStatsBox.put(currentSystemStats)
//
//            skillNodeEntityBox.putBatched(currentSkillTreeStats.arrayStats, currentSkillTreeStats.arrayStats.size)
//            mobSkillTreeStatsBox.put(currentSkillTreeStats)
//        }
//    }

    fun getBox() = currentBox

    fun getAllBoxes() : ArrayList<Box<*>> {
        val list = ArrayList<Box<*>>()
        applicationBox.allEntityClasses.forEach {
            list.add(applicationBox.boxFor(it))
        }
        return list
    }
}