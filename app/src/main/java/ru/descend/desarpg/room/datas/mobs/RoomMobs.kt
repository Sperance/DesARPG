package ru.descend.desarpg.room.datas.mobs

import ru.descend.desarpg.logic.BattleStats
import ru.descend.desarpg.logic.InventoryMob
import java.util.UUID

data class RoomMobs(
    var mobId: Int = 0,
    val name: String,
    val mobUUID: String = UUID.randomUUID().toString(),
    val mobLevel: Byte = 1,
    var battleStatsStr: String = ""
) {
    var battleStats = BattleStats()
    var inventoryItems = InventoryMob()

    fun onAttack(enemy: RoomMobs) {
        val curDamage = battleStats.attackPhysic.getWithPercent()
        enemy.battleStats.health.remove(curDamage)
    }

    fun mainInit() {
        battleStats.healthForStrength.set(5)
        battleStats.attackForStrength.set(2)
    }
}