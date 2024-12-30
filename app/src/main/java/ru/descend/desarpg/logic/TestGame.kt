package ru.descend.desarpg.logic

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class TestGame {

    @Test
    fun testBaseStats() {
        val pers1 = Mob("Игрок")
        pers1.battleStats.health.set(100)
        pers1.battleStats.health.setPercent(20)
        pers1.battleStats.strength.set(5)
//        println("strength: ${pers1.battleStats.strength}")

        pers1.battleStats.health.prepareInit()
        println("health: ${pers1.battleStats.health}")
//        pers1.battleStats.strength.prepareInit()
        pers1.battleStats.health.removeCurrent(10)
        pers1.battleStats.health.removeCurrent(20)
        pers1.battleStats.health.removeCurrent(50)
//        pers1.battleStats.health.removeCurrent(10)

        println("health: ${pers1.battleStats.health}")
//        println("strength: ${pers1.battleStats.strength}")
    }

    @Test
    fun testSerialization() {
        val pers1 = Mob("Игрок")
        pers1.battleStats.attackPhysic.set(25)
        pers1.battleStats.attackPhysic.setPercent(10)
        pers1.battleStats.health.set(12.5)
        pers1.battleStats.strength.set(3)
        pers1.battleStats.health.setPercent(10.2)

        println("health: ${pers1.battleStats.health}")

        val string = Json.encodeToString(pers1)
        println("before: ${pers1.battleStats}")
        println(string)

        pers1.battleStats.health.set(42.5)

        val newPers = Json.decodeFromString<Mob>(string)
        println("health: ${newPers.battleStats.health}")
    }

    @Test
    fun test1() {
        val pers1 = Mob("Игрок")
        pers1.battleStats.attackPhysic.set(10)
        pers1.battleStats.health.set(50)
        pers1.battleStats.health.setPercent(10)
        pers1.battleStats.strength.set(5)
        pers1.battleStats.attackPhysic.setPercent(20)
        val pers2 = Mob("Враг")
        pers2.battleStats.attackPhysic.set(8)
        pers2.battleStats.health.set(250)

        val battle = BattleObject(pers1, pers2)
        runBlocking {
            battle.doBattle()
        }
        println("P1: ${pers1.battleStats.health}")
        println("P2: ${pers2.battleStats.health}")

        val battle2 = BattleObject(pers1, pers2)
        runBlocking {
            battle2.doBattle()
        }
        println("P1: ${pers1.battleStats.health}")
        println("P2: ${pers2.battleStats.health}")
    }
}