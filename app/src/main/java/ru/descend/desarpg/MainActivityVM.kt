package ru.descend.desarpg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.objectbox.Box
import io.objectbox.BoxStore
import kotlinx.coroutines.launch
import ru.descend.desarpg.model.EnumSystemStatsType
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobSystemStats
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.model.SystemStatsProp
import ru.descend.desarpg.model.RoomMobs
import java.sql.Date

fun MainActivityVM.launch(body: suspend () -> Unit) = viewModelScope.launch { body.invoke() }
class MainActivityVM(app: Application) : AndroidViewModel(app) {

    lateinit var currentBox: BoxStore
    lateinit var currentMob: RoomMobs
    lateinit var currentStats: MobBattleStats
    lateinit var currentSystemStats: MobSystemStats

    private lateinit var roomMobsBox: Box<RoomMobs>
    private lateinit var battleStatsBox: Box<MobBattleStats>
    private lateinit var statsPropBox: Box<StockStatsProp>
    private lateinit var systemStatsBox: Box<MobSystemStats>
    private lateinit var systemPropBox: Box<SystemStatsProp>

    fun saveToDB() {
        currentBox.runInTx {
            statsPropBox.putBatched(currentStats.arrayStats, currentStats.arrayStats.size)
            battleStatsBox.put(currentStats)

            systemPropBox.putBatched(currentSystemStats.arrayStats, currentSystemStats.arrayStats.size)
            systemStatsBox.put(currentSystemStats)
        }
    }

    fun initialize() {
        roomMobsBox = currentBox.boxFor(RoomMobs::class.java)
        battleStatsBox = currentBox.boxFor(MobBattleStats::class.java)
        statsPropBox = currentBox.boxFor(StockStatsProp::class.java)
        systemStatsBox = currentBox.boxFor(MobSystemStats::class.java)
        systemPropBox = currentBox.boxFor(SystemStatsProp::class.java)

//        val battleStatsBox = currentBox.boxFor(MobBattleStats::class.java)
//        val statsPropBox = currentBox.boxFor(StockStatsProp::class.java)
//
//        val systemStatsBox = currentBox.boxFor(MobSystemStats::class.java)
//        val systemPropBox = currentBox.boxFor(SystemStatsProp::class.java)

        if (roomMobsBox.get(1) != null) {
            currentMob = roomMobsBox.get(1)
            currentStats = battleStatsBox.get(1)
            currentSystemStats = systemStatsBox.get(1)
            return
        }

        val roomMob = RoomMobs(name = "TestPlayer")
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
        initializeObservers()
    }

    private fun initializeObservers() {
        battleStatsBox.store.subscribe().observer {
            log("changed")
        }
    }

    fun startWork() {
        log("startWork")
        val stat = currentSystemStats.getStockStat(EnumSystemStatsType.LAST_ENTER_DATE)
        val statDate = Date(stat.value.toLong())
        val currentDate = Date(System.currentTimeMillis())
        if (statDate.year == currentDate.year && statDate.month == currentDate.month && statDate.day == currentDate.day) {
            log("all OK")
        }
        if (statDate > currentDate) {
            log("WTF")
        }
        stat.value = currentDate.time.toDouble()
        currentSystemStats.getStockStat(EnumSystemStatsType.COUNT_ENTER_SYSTEM).add(1)
        saveToDB()
    }
}