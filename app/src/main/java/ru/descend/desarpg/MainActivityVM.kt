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

    fun getBattleStats() = repoMob.currentStats
    fun getSkillTreeNodes() = repoMob.currentSkillTreeStats
    fun getMob() = repoMob.currentMob
    fun getAllBoxes() = repoMob.getAllBoxes()

    fun saveToDB() {
        repoMob.saveToDB()
    }
}