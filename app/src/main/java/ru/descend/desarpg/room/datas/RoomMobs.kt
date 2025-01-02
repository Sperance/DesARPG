package ru.descend.desarpg.room.datas

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.descend.desarpg.logic.BattleStats
import java.util.UUID

@Entity
data class RoomMobs(
    @PrimaryKey(autoGenerate = true) var mobId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "UUID") val mobUUID: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "level") val mobLevel: Byte = 1,
    @ColumnInfo(name = "battleStats") var battleStatsStr: String = ""
) {
    @Ignore var battleStats = BattleStats()

    fun onAttack(enemy: RoomMobs) {
        val curDamage = battleStats.attackPhysic.getWithPercent()
        enemy.battleStats.health.remove(curDamage)
    }

    fun mainInit() {
        battleStats.healthForStrength.set(5)
        battleStats.attackForStrength.set(2)
    }

    fun toSerializeRoom() {
        battleStatsStr = Json.encodeToString(this.battleStats)
    }

    fun fromSerializeRoom() {
        battleStats = Json.decodeFromString<BattleStats>(battleStatsStr)
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

    @Insert(onConflict = OnConflictStrategy.ABORT)
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