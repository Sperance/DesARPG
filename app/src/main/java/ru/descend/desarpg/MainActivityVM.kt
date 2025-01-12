package ru.descend.desarpg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.descend.desarpg.room.AppDatabase
import ru.descend.desarpg.room.datas.RoomMobs
fun MainActivityVM.launch(body: suspend () -> Unit) = viewModelScope.launch { body.invoke() }
class MainActivityVM(app: Application) : AndroidViewModel(app) {

    private val dataBase = AppDatabase(app)
    lateinit var currentMob: RoomMobs

    fun initialize() = viewModelScope.launch {
//        dataBase.daoMobs().deleteAll()
        if (dataBase.daoMobs().getFromId(1) == null) {
            val pers1 = RoomMobs(mobId = 1, name = "Игрок")
            pers1.battleStats.attackPhysic.set(10)
            pers1.battleStats.health.set(50)
            pers1.battleStats.health.setPercent(10)
            pers1.battleStats.strength.set(5)
            pers1.battleStats.attackPhysic.setPercent(20)
            pers1.mainInit()
            pers1.toSerializeRoom()
            dataBase.daoMobs().insert(pers1)
        }
        println("ALL MOBS: ${dataBase.daoMobs().getAll()}")
        currentMob = dataBase.daoMobs().getFromId(1)!!
        currentMob.fromSerializeRoom()
    }

    fun updateMob() = viewModelScope.launch {
        currentMob.toSerializeRoom()
        println("UPDATED: " + dataBase.daoMobs().update(currentMob))
    }

    suspend fun getAllMobs() = dataBase.daoMobs().getAll()

    fun clearAllMobs() = viewModelScope.launch {
        dataBase.daoMobs().deleteAll()
    }

    fun clearAllItems() = viewModelScope.launch {
        dataBase.daoItems().deleteAll()
    }
}