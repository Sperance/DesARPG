package ru.descend.desarpg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.descend.desarpg.logic.Mob
import ru.descend.desarpg.room.AppDatabase
import ru.descend.desarpg.room.datas.RoomMobs.Companion.toMob
import ru.descend.desarpg.room.datas.RoomMobs.Companion.toRoom
import ru.descend.desarpg.room.datas.RoomUsers
fun MainActivityVM.launch(body: suspend () -> Unit) = viewModelScope.launch { body.invoke() }
class MainActivityVM(app: Application) : AndroidViewModel(app) {

    private val dataBase = AppDatabase(app)


//    val getVMScope = viewModelScope

    fun addUser(user: RoomUsers) = viewModelScope.launch {
        dataBase.daoUsers().insert(user)
    }

    fun addMobToRoom(mob: Mob) = viewModelScope.launch {
        dataBase.daoMobs().insert(mob.toRoom())
        println("DATAS: ${dataBase.daoMobs().getAll().joinToString("\n")}")
    }

    fun getMobFromRoom(id: Int) = viewModelScope.launch {
        val mob = dataBase.daoMobs().getFromId(id)?.toMob()
        println("MOB: $mob")
    }

    suspend fun getAllMobs() = dataBase.daoMobs().getAll()

    fun clearAllMobs() = viewModelScope.launch {
        dataBase.daoMobs().deleteAll()
    }
}