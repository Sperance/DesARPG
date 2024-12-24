package ru.descend.desarpg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.descend.desarpg.room.AppDatabase
import ru.descend.desarpg.room.datas.RoomUsers

class MainActivityVM(app: Application) : AndroidViewModel(app) {

    private val dataBase = AppDatabase(app)

    suspend fun addUser(user: RoomUsers) {
        dataBase.daoUsers().insert(user)
    }
}