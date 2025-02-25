package ru.descend.desarpg

import androidx.lifecycle.ViewModel
import io.objectbox.android.ObjectBoxLiveData
import ru.descend.desarpg.model.BaseItem
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobMain
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.repository.BoxMobRepository

class MainActivityVM(private val repoMob: BoxMobRepository) : ViewModel() {

    private var mobBattleStatsLiveData: ObjectBoxLiveData<MobBattleStats?>? = null
    fun getMobBattleStatsLiveData(): ObjectBoxLiveData<MobBattleStats?> {
        if (mobBattleStatsLiveData == null) {
            mobBattleStatsLiveData = ObjectBoxLiveData(applicationBox.boxFor(MobBattleStats::class.java).query().build())
        }
        return mobBattleStatsLiveData as ObjectBoxLiveData<MobBattleStats?>
    }

    private var mobLiveData: ObjectBoxLiveData<MobMain?>? = null
    fun getMobLiveData(): ObjectBoxLiveData<MobMain?> {
        if (mobLiveData == null) {
            mobLiveData = ObjectBoxLiveData(applicationBox.boxFor(MobMain::class.java).query().build())
        }
        return mobLiveData as ObjectBoxLiveData<MobMain?>
    }

    private var stockStatsPropLiveData: ObjectBoxLiveData<StockStatsProp?>? = null
    fun getStockStatsPropLiveData(): ObjectBoxLiveData<StockStatsProp?> {
        if (stockStatsPropLiveData == null) {
            stockStatsPropLiveData = ObjectBoxLiveData(applicationBox.boxFor(StockStatsProp::class.java).query().build())
        }
        return stockStatsPropLiveData as ObjectBoxLiveData<StockStatsProp?>
    }

    private var itemsLiveData: ObjectBoxLiveData<BaseItem?>? = null
    fun getItemsLiveData(): ObjectBoxLiveData<BaseItem?> {
        if (itemsLiveData == null) {
            itemsLiveData = ObjectBoxLiveData(applicationBox.boxFor(BaseItem::class.java).query().build())
        }
        return itemsLiveData as ObjectBoxLiveData<BaseItem?>
    }

    fun getMob() = repoMob.getCurrentMob()
    fun getCurrentStats() = repoMob.getCurrentStats()
    fun getCurrentSystemStats() = repoMob.getCurrentSystemStats()
    fun getStockStats() = repoMob.getStockStats()
    fun getSkillTreeNodes() = repoMob.getCurrentSkillTreeStats()
    fun getInventory() = repoMob.getCurrentInventory()
    fun testInventory() = repoMob.test_inventory_mob()
}