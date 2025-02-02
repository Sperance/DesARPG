package ru.descend.desarpg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.descend.desarpg.room.datas.mobs.RoomMobs

fun MainActivityVM.launch(body: suspend () -> Unit) = viewModelScope.launch { body.invoke() }
class MainActivityVM(app: Application) : AndroidViewModel(app) {

//    private val dataBase = AppDatabase(app)
//    val repoItems = ItemsRepository(dataBase.daoItems())
//    val repoMobs = MobsRepository(dataBase.daoMobs())
    lateinit var currentMob: RoomMobs

    fun initialize() = viewModelScope.launch {
//        repoMobs.deleteAll()
//        repoItems.deleteAll()
//        if (repoMobs.getAll().isEmpty()) {
//            val pers1 = RoomMobs(name = "Игрок")
//            pers1.battleStats.attackPhysic.set(10)
//            pers1.battleStats.health.set(50)
//            pers1.battleStats.health.setPercent(10)
//            pers1.battleStats.strength.set(5)
//            pers1.battleStats.attackPhysic.setPercent(20)
//            pers1.mainInit()
//            pers1.toSerializeRoom()
//            repoMobs.insert(pers1)
//        }
//        val allMobs = repoMobs.getAll()
//        log("ALL MOBS: $allMobs")
//        currentMob = allMobs.last()
//        currentMob.fromSerializeRoom()
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