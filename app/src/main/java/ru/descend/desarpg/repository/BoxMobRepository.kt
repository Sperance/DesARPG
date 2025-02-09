package ru.descend.desarpg.repository

import io.objectbox.Box
import io.objectbox.BoxStore
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobSystemStats
import ru.descend.desarpg.model.RoomMobs
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.model.SystemStatsProp

class BoxMobRepository(private val currentBox: BoxStore) {
    private val roomMobsBox: Box<RoomMobs> = currentBox.boxFor(RoomMobs::class.java)
    val battleStatsBox: Box<MobBattleStats> = currentBox.boxFor(MobBattleStats::class.java)
    private val statsPropBox: Box<StockStatsProp> = currentBox.boxFor(StockStatsProp::class.java)
    private val systemStatsBox: Box<MobSystemStats> = currentBox.boxFor(MobSystemStats::class.java)
    private val systemPropBox: Box<SystemStatsProp> = currentBox.boxFor(SystemStatsProp::class.java)

    lateinit var currentMob: RoomMobs
    lateinit var currentStats: MobBattleStats
    lateinit var currentSystemStats: MobSystemStats

    init {
        if (roomMobsBox.get(1) != null) {
            currentMob = roomMobsBox.get(1)
            currentStats = battleStatsBox.get(1)
            currentSystemStats = systemStatsBox.get(1)
        } else {
            val roomMob = RoomMobs().apply { name = "TestUser" }
            val battleStats = MobBattleStats()
            val systemStats = MobSystemStats()
            currentBox.runInTx {
                battleStats.initializeAllStats()
                systemStats.initializeAllStats()

                roomMob.battleStats.target = battleStats
                roomMob.systemStats.target = systemStats

                statsPropBox.put(battleStats.arrayStats)
                systemPropBox.put(systemStats.arrayStats)

                battleStatsBox.put(battleStats)
                systemStatsBox.put(systemStats)

                roomMobsBox.put(roomMob)

                currentMob = roomMobsBox.get(roomMob.id)
                currentStats = battleStatsBox.get(battleStats.id)
                currentSystemStats = systemStatsBox.get(systemStats.id)
            }
        }
    }

    fun saveToDB() {
        currentBox.runInTx {
            statsPropBox.putBatched(currentStats.arrayStats, currentStats.arrayStats.size)
            battleStatsBox.put(currentStats)

            systemPropBox.putBatched(currentSystemStats.arrayStats, currentSystemStats.arrayStats.size)
            systemStatsBox.put(currentSystemStats)
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