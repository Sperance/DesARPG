package ru.descend.desarpg.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.descend.desarpg.room.datas.DaoItems
import ru.descend.desarpg.room.datas.DaoUsers
import ru.descend.desarpg.room.datas.RoomItems
import ru.descend.desarpg.room.datas.RoomUsers

@Database(entities = [RoomUsers::class, RoomItems::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoUsers(): DaoUsers
    abstract fun daoItems(): DaoItems
}

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null
    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(RoomDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "desarpg-main-database-inventory-18cdb9ce-735b-4230-a080-5c8d3ae28baf"
        ).build()
}

class DatabaseHelperImpl(private val appDatabase: AppDatabase) {
    suspend fun usersGetAll() = appDatabase.daoUsers().getAll()
    suspend fun usersGetFromId(id: Int) = appDatabase.daoUsers().getFromId(id)
    suspend fun usersInsertAll(values: List<RoomUsers>) = appDatabase.daoUsers().insertAll(values)
    suspend fun usersInsert(value: RoomUsers) = appDatabase.daoUsers().insert(value)
    suspend fun usersUpdateAll(values: List<RoomUsers>) = appDatabase.daoUsers().updateAll(values)
    suspend fun usersUpdate(value: RoomUsers) = appDatabase.daoUsers().update(value)
    suspend fun usersDeleteAll() = appDatabase.daoUsers().deleteAll()
    suspend fun usersDelete(value: RoomUsers) = appDatabase.daoUsers().delete(value)
}