package ru.descend.desarpg.repository

import io.objectbox.BoxStore
import ru.descend.desarpg.model.Mob
import ru.descend.desarpg.model.MobBattleStats

class MobBattleStatsRepository(boxStore: BoxStore) : BaseObjectBoxRepository<MobBattleStats>(boxStore.boxFor(MobBattleStats::class.java)) {

    private var localBattleStats: MobBattleStats? = null
    fun getBattleStats() : MobBattleStats {
        if (getById(1) == null || localBattleStats == null) {
            val battleStats = MobBattleStats()
            battleStats.initializeAllStats()
            val savedId = save(battleStats)
            localBattleStats = getById(savedId)
            return localBattleStats!!
        } else {
            return localBattleStats!!
        }
    }
}