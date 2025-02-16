package ru.descend.desarpg

import androidx.lifecycle.ViewModel
import io.objectbox.android.ObjectBoxLiveData
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobBattleStats_
import ru.descend.desarpg.repository.BoxMobRepository

class MainActivityVM(private val repoMob: BoxMobRepository) : ViewModel() {

    private var mobBattleStatsLiveData: ObjectBoxLiveData<MobBattleStats?>? = null
    fun getMobBattleStatsLiveData(): ObjectBoxLiveData<MobBattleStats?> {
        if (mobBattleStatsLiveData == null) {
            mobBattleStatsLiveData = ObjectBoxLiveData(repoMob.battleStatsBox.query().order(MobBattleStats_.id).build())
        }
        return mobBattleStatsLiveData as ObjectBoxLiveData<MobBattleStats?>
    }

    fun getCurrentStats() = repoMob.getCurrentStats()
    fun getCurrentSystemStats() = repoMob.getCurrentSystemStats()
    fun getStockStats() = repoMob.getStockStats()
    fun getSkillTreeNodes() = repoMob.getCurrentSkillTreeStats()
    fun getAllBoxes() = repoMob.getAllBoxes()
    fun getBox() = repoMob.getBox()

    fun saveToDB() {
        repoMob.saveToDB()
    }
}