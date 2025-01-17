package ru.descend.desarpg.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.descend.desarpg.room.converters.StockStatsBoolConverter
import ru.descend.desarpg.room.converters.StockStatsValueConverter
import ru.descend.desarpg.room.datas.DaoMobs
import ru.descend.desarpg.room.datas.RoomMobs
import ru.descend.desarpg.room.datas.items.DaoItems
import ru.descend.desarpg.room.datas.items.RoomItems
import java.util.UUID

@Database(entities =
[RoomItems::class, RoomMobs::class],
    version = 1,
    exportSchema = true)
@TypeConverters(StockStatsBoolConverter::class, StockStatsValueConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoItems(): DaoItems
    abstract fun daoMobs(): DaoMobs

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
//            AppDatabase::class.java, "desarpg-main-1.db")
            AppDatabase::class.java, "desarpg-main-${UUID.randomUUID()}.db")
            .allowMainThreadQueries()
            .build()
    }
}