package ru.descend.desarpg.repository

import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.descend.desarpg.log
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobSkillTreeStats
import ru.descend.desarpg.model.MobSystemStats
import ru.descend.desarpg.model.SkillNodeEntity
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.model.SystemStatsProp
import java.util.UUID

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
        if (battleStatsBox.get(1) != null) {
            log("already")
            currentStats = battleStatsBox.get(1)
            log("cur: $currentStats")
            currentSystemStats = systemStatsBox.get(1)
            currentSkillTreeStats = mobSkillTreeStatsBox.get(1)
        } else {
            createAndSaveMobBattleStats()
            log("created")
            val battleStats = MobBattleStats()
            val systemStats = MobSystemStats()
            val mobSkillTreeStats = MobSkillTreeStats()
            currentBox.runInTx {
                battleStats.initializeAllStats()
                systemStats.initializeAllStats()
                mobSkillTreeStats.initializeAllStats()

                statsPropBox.put(battleStats.arrayStats)
                systemPropBox.put(systemStats.arrayStats)
                skillNodeEntityBox.put(mobSkillTreeStats.arrayStats)

                systemStatsBox.put(systemStats)
                mobSkillTreeStatsBox.put(mobSkillTreeStats)

                currentStats = battleStatsBox.get(battleStatsBox.put(battleStats))
                log("cur: $currentStats")
                log("old: $battleStats")
                currentSystemStats = systemStats
                currentSkillTreeStats = mobSkillTreeStats
            }
        }
    }

    fun createAndSaveMobBattleStats() {

        val boxOuterClass = currentBox.boxFor(OuterClasses::class.java)

        val newObject = OuterClasses()
        newObject.arrayStats.add(InnerClasses(type = 1))
        newObject.arrayStats.add(InnerClasses(type = 2))
        newObject.arrayStats.add(InnerClasses(type = 3))
        newObject.arrayStats.add(InnerClasses(type = 4))
        val id = boxOuterClass.put(newObject)
        log("1: $newObject")
        val catchedObject = boxOuterClass.get(id)
        log("2: $catchedObject")
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