package ru.descend.desarpg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.objectbox.BoxStore
import kotlinx.coroutines.launch
import ru.descend.desarpg.logic.BattleStats
import ru.descend.desarpg.logic.EnumPropsType
import ru.descend.desarpg.logic.StockStatsProp
import ru.descend.desarpg.room.datas.mobs.RoomMobs

fun MainActivityVM.launch(body: suspend () -> Unit) = viewModelScope.launch { body.invoke() }
class MainActivityVM(app: Application) : AndroidViewModel(app) {

    lateinit var currentBox: BoxStore
    lateinit var currentMob: RoomMobs

    fun initialize() {
        val roomMobsBox = currentBox.boxFor(RoomMobs::class.java)
        val battleStatsBox = currentBox.boxFor(BattleStats::class.java)
        val statsPropBox = currentBox.boxFor(StockStatsProp::class.java)

        if (roomMobsBox.get(1) != null) {
            currentMob = roomMobsBox.get(1)
            return
        }

        val roomMob = RoomMobs(name = "TestPlayer")
        val battleStats = BattleStats()

        val healthStat = StockStatsProp(name = "Health", type = EnumPropsType.HEALTH, valueP = 100.0)
        val attackStat = StockStatsProp(name = "Damage", type = EnumPropsType.PHYSIC_ATTACK, valueP = 5.0)
        val strengthStat = StockStatsProp(name = "Strength", type = EnumPropsType.STRENGTH, valueP = 1.0)

        currentBox.runInTx {
            battleStats.arrayStats.addAll(listOf(healthStat, attackStat, strengthStat))
            roomMob.battleStats.target = battleStats

            statsPropBox.put(healthStat)
            statsPropBox.put(attackStat)
            statsPropBox.put(strengthStat)
            battleStatsBox.put(battleStats)
            roomMobsBox.put(roomMob)

            currentMob = roomMobsBox.get(roomMob.id)
        }
    }

    fun updateMob() = viewModelScope.launch {
//        currentMob.toSerializeRoom()
//        repoMobs.update(currentMob)

        println("BEFORE: $currentMob")

//        val allMobs = repoMobs.getAll()
//        currentMob = allMobs.last()
//        currentMob.fromSerializeRoom()

        println("AFTER: $currentMob")
    }
}