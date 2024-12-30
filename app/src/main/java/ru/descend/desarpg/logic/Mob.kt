package ru.descend.desarpg.logic

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.reflect.full.declaredMemberProperties

interface IntBattleChanges {
    fun onBeginBattle(mob: Mob, enemy: Mob) {}
}

@Serializable
data class Mob(val name: String): IntBattleChanges {
    var uuid: String = UUID.randomUUID().toString()
    @OptIn(ExperimentalSerializationApi::class)
    @EncodeDefault var level: Byte = 1
    var battleStats: BattleStats = BattleStats()

    init {
        BattleStats::class.declaredMemberProperties.forEach { (it.call(battleStats) as PropertyValue).mob = this }
    }

    fun onBeginBattle(enemy: Mob) {
        //BattleStats::class.declaredMemberProperties.forEach { (it.call(battleStats) as PropertyValue).onBeginBattle() }
        BattleStats::class.declaredMemberProperties.forEach { (it.call(battleStats) as PropertyValue).onBeginBattle(this, enemy) }
    }

    fun onEndBattle(enemy: Mob) {
        //BattleStats::class.declaredMemberProperties.forEach { (it.call(battleStats) as PropertyValue).onEndBattle() }
    }

    fun onAttack(enemy: Mob) {
        val curDamage = battleStats.attackPhysic.getCurrent()
        enemy.battleStats.health.removeCurrent(curDamage)
    }

    override fun toString(): String {
        return "Mob(name='$name', uuid='$uuid', level=$level, battleStats=$battleStats)"
    }
}