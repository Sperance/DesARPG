package ru.descend.desarpg.room.datas.items

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoItems {
    @Query("SELECT * FROM items_table")
    fun getAll(): List<RoomItems>

    @Query("SELECT * FROM items_table")
    fun getAllLiveData(): LiveData<List<RoomItems>>

    @Query("SELECT * FROM items_table WHERE itemId = :id")
    fun getFromId(id: Int): RoomItems?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(values: List<RoomItems>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(value: RoomItems)

    @Update
    fun updateAll(values: List<RoomItems>): Int

    @Update
    fun update(value: RoomItems): Int

    @Query("DELETE FROM items_table")
    fun deleteAll()

    @Delete
    fun delete(value: RoomItems)
}