package ru.descend.desarpg.room.datas

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import java.util.UUID

@Entity
data class RoomItems(
    @PrimaryKey(autoGenerate = true) val itemId: Int,
    @ColumnInfo(name = "itemName") val name: String,
    @ColumnInfo(name = "itemDescription") val description: String = "",
    @ColumnInfo(name = "itemIdentity") val identity: String = UUID.randomUUID().toString()
)

@Dao
interface DaoItems {
    @Query("SELECT * FROM RoomItems")
    suspend fun getAll(): List<RoomItems>

    @Query("SELECT * FROM RoomItems WHERE itemId = :id")
    suspend fun getFromId(id: Int): RoomItems?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(values: List<RoomItems>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(value: RoomItems)

    @Update
    suspend fun updateAll(values: List<RoomItems>): Int

    @Update
    suspend fun update(value: RoomItems): Int

    @Query("DELETE FROM RoomItems")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(value: RoomItems)
}