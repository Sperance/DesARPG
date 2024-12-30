package ru.descend.desarpg.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.descend.desarpg.room.datas.DaoItems
import ru.descend.desarpg.room.datas.DaoMobs
import ru.descend.desarpg.room.datas.DaoUsers
import ru.descend.desarpg.room.datas.RoomItems
import ru.descend.desarpg.room.datas.RoomMobs
import ru.descend.desarpg.room.datas.RoomUsers

@Database(entities =
[RoomUsers::class, RoomItems::class, RoomMobs::class],
    version = 1,
    exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoUsers(): DaoUsers
    abstract fun daoItems(): DaoItems
    abstract fun daoMobs(): DaoMobs

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "desarpg-main-database-inventory-28cdb9ce-735b-4230-a080-5c8d3ae28baf.db")
            .build()
    }
}