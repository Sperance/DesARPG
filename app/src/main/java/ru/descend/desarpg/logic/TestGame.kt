package ru.descend.desarpg.logic

import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestGame {

    @Test
    fun test1() {

        val pers1 = Mob("Игрок")
        pers1.battleStats.AttackPhysic.set(25.0)
        pers1.battleStats.AttackPhysic.stockPercent += 10
        val pers2 = Mob("Враг")
        pers2.battleStats.AttackPhysic.set(8.0)
        pers2.battleStats.Health.set(250.0)

        val battle = BattleObject(pers1, pers2)
        runBlocking {
            battle.doBattle()
        }
    }
}