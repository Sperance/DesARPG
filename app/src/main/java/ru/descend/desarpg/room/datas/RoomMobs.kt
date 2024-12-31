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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.descend.desarpg.logic.BattleStats
import ru.descend.desarpg.logic.Mob
import java.util.UUID

@Entity
data class RoomMobs(
    @PrimaryKey(autoGenerate = true) var mobId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "UUID") val mobUUID: String,
    @ColumnInfo(name = "level") val mobLevel: Byte,
    @ColumnInfo(name = "battleStats") val battleStats: String
) {
    companion object {
        fun Mob.toRoom(): RoomMobs {
            return RoomMobs(mobId = 1, name = this.name, mobUUID = this.uuid, mobLevel = this.level, battleStats = Json.encodeToString(this.battleStats))
        }
        fun RoomMobs.toMob(): Mob {
            return Mob(name = this.name).apply {
                this.uuid = this@toMob.mobUUID
                this.level = this@toMob.mobLevel
                this.battleStats = Json.decodeFromString<BattleStats>(this@toMob.battleStats)
            }
        }
    }
}

@Dao
interface DaoMobs {
    @Query("SELECT * FROM RoomMobs")
    suspend fun getAll(): List<RoomMobs>

    @Query("SELECT * FROM RoomMobs WHERE mobId = :id")
    suspend fun getFromId(id: Int): RoomMobs?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(values: List<RoomMobs>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(value: RoomMobs)

    @Update
    suspend fun updateAll(values: List<RoomMobs>): Int

    @Update
    suspend fun update(value: RoomMobs): Int

    @Query("DELETE FROM RoomMobs")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(value: RoomMobs)
}