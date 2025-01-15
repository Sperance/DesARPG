package ru.descend.desarpg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.descend.desarpg.room.AppDatabase
import ru.descend.desarpg.room.datas.RoomMobs
import ru.descend.desarpg.room.datas.items.ItemsRepository

fun MainActivityVM.launch(body: suspend () -> Unit) = viewModelScope.launch { body.invoke() }
class MainActivityVM(app: Application) : AndroidViewModel(app) {

    private val dataBase = AppDatabase(app)
    val repoItems = ItemsRepository(dataBase.daoItems())
    lateinit var currentMob: RoomMobs

    fun initialize(str: String) = viewModelScope.launch {
        clearAllMobs()
        repoItems.deleteAll()
        log("[CLEARS COMPLETED] $str ${this.hashCode()}")
        if (dataBase.daoMobs().getAll().isEmpty()) {
            val pers1 = RoomMobs(name = "Игрок")
            pers1.battleStats.attackPhysic.set(10)
            pers1.battleStats.health.set(50)
            pers1.battleStats.health.setPercent(10)
            pers1.battleStats.strength.set(5)
            pers1.battleStats.attackPhysic.setPercent(20)
            pers1.mainInit()
            pers1.toSerializeRoom()
            dataBase.daoMobs().insert(pers1)
        }
        val allMobs = dataBase.daoMobs().getAll()
        log("ALL MOBS: $allMobs")
        currentMob = allMobs.last()
        currentMob.fromSerializeRoom()
    }

    fun updateMob() = viewModelScope.launch {
        currentMob.toSerializeRoom()
        dataBase.daoMobs().update(currentMob)

        println("BEFORE: $currentMob")

        val allMobs = dataBase.daoMobs().getAll()
        currentMob = allMobs.last()
        currentMob.fromSerializeRoom()

        println("AFTER: $currentMob")
    }

    suspend fun getAllMobs() = dataBase.daoMobs().getAll()

    suspend fun clearAllMobs() {
        dataBase.daoMobs().deleteAll()
    }
}