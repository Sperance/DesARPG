package ru.descend.desarpg

import androidx.lifecycle.ViewModel
import io.objectbox.android.ObjectBoxLiveData
import ru.descend.desarpg.model.BaseItem
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobMain
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.repository.BoxMobRepository

class MainActivityVM(private val repoMob: BoxMobRepository) : ViewModel() {

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
    fun getWorkStats() = repoMob.getCurrentWorkStats()
    fun getInventory() = repoMob.getCurrentInventory()
}