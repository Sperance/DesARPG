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
data class RoomUsers(
    @PrimaryKey val userId: Int,
    @ColumnInfo(name = "userName") val name: String = "",
    @ColumnInfo(name = "userPassword") val password: String?,
    @ColumnInfo(name = "userIdentity") val identity: String = UUID.randomUUID().toString()
)

@Dao
interface DaoUsers {
    @Query("SELECT * FROM RoomUsers")
    suspend fun getAll(): List<RoomUsers>

    @Query("SELECT * FROM RoomUsers WHERE userId = :id")
    suspend fun getFromId(id: Int): RoomUsers?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(values: List<RoomUsers>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(value: RoomUsers)

    @Update
    suspend fun updateAll(values: List<RoomUsers>): Int

    @Update
    suspend fun update(value: RoomUsers): Int

    @Query("DELETE FROM RoomUsers")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(value: RoomUsers)
}