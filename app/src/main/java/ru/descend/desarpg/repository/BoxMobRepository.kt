package ru.descend.desarpg.repository

import io.objectbox.Box
import io.objectbox.BoxStore
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobSkillTreeStats
import ru.descend.desarpg.model.MobSystemStats
import ru.descend.desarpg.model.RoomMobs
import ru.descend.desarpg.model.SkillNodeEntity
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.model.SystemStatsProp

class BoxMobRepository(private val currentBox: BoxStore) {
    private val roomMobsBox: Box<RoomMobs> = currentBox.boxFor(RoomMobs::class.java)
    lateinit var currentMob: RoomMobs

    val battleStatsBox: Box<MobBattleStats> = currentBox.boxFor(MobBattleStats::class.java)
    private val statsPropBox: Box<StockStatsProp> = currentBox.boxFor(StockStatsProp::class.java)
    lateinit var currentStats: MobBattleStats

    private val systemStatsBox: Box<MobSystemStats> = currentBox.boxFor(MobSystemStats::class.java)
    private val systemPropBox: Box<SystemStatsProp> = currentBox.boxFor(SystemStatsProp::class.java)
    lateinit var currentSystemStats: MobSystemStats

    private val mobSkillTreeStatsBox: Box<MobSkillTreeStats> = currentBox.boxFor(MobSkillTreeStats::class.java)
    private val skillNodeEntityBox: Box<SkillNodeEntity> = currentBox.boxFor(SkillNodeEntity::class.java)
    lateinit var currentSkillTreeStats: MobSkillTreeStats

    init {
        if (roomMobsBox.get(1) != null) {
            currentMob = roomMobsBox.get(1)
            currentStats = battleStatsBox.get(1)
            currentSystemStats = systemStatsBox.get(1)
            currentSkillTreeStats = mobSkillTreeStatsBox.get(1)
        } else {
            val roomMob = RoomMobs().apply { name = "TestUser" }
            val battleStats = MobBattleStats()
            val systemStats = MobSystemStats()
            val mobSkillTreeStats = MobSkillTreeStats()
            currentBox.runInTx {
                battleStats.initializeAllStats()
                systemStats.initializeAllStats()
                mobSkillTreeStats.initializeAllStats()

                roomMob.battleStats.target = battleStats
                roomMob.systemStats.target = systemStats
                roomMob.skillTreeStats.target = mobSkillTreeStats

                statsPropBox.put(battleStats.arrayStats)
                systemPropBox.put(systemStats.arrayStats)
                skillNodeEntityBox.put(mobSkillTreeStats.arrayStats)

                battleStatsBox.put(battleStats)
                systemStatsBox.put(systemStats)
                mobSkillTreeStatsBox.put(mobSkillTreeStats)

                roomMobsBox.put(roomMob)

                currentMob = roomMobsBox.get(roomMob.id)
                currentStats = battleStatsBox.get(battleStats.id)
                currentSystemStats = systemStatsBox.get(systemStats.id)
                currentSkillTreeStats = mobSkillTreeStatsBox.get(mobSkillTreeStats.id)
            }
        }
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