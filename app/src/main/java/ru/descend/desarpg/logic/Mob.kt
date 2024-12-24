package ru.descend.desarpg.logic

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Mob(val name: String) : IntBaseValues {
    val uuid: String = UUID.randomUUID().toString()
    var level: Byte = 1
    var battleStats = BattleStats()
    var baseStats = BaseStats()
    var boolStats = BoolStats()

    init {
        battleStats.Health.arrayListeners.addListener {
            if (battleStats.Health.getBattle() <= 0.0) {
                boolStats.isAlive.set(false)
                println("$name is DEAD")
            }
        }
    }

    fun onAttack(enemy: Mob) {
        if (!boolStats.isCanAttack.get()) return
        if (!enemy.boolStats.isAlive.get()) return
        val curDamage = battleStats.AttackPhysic.getBattle()
        enemy.battleStats.Health.removeBattle(curDamage)
        onDoDamage(this, enemy)
        enemy.onTakeDamage(enemy, this)
    }
}