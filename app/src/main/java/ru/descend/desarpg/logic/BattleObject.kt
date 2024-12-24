package ru.descend.desarpg.logic

import kotlinx.coroutines.delay

class BattleObject(private val person1: Mob, private val person2: Mob) {

    private var isBattleActive = false
    private var countSeconds = 0

    suspend fun doBattle() {
        isBattleActive = true
        person1.battleStats.initForBattle()
        person2.battleStats.initForBattle()
        while (isBattleActive && person1.boolStats.isAlive.get() && person2.boolStats.isAlive.get()) {
            if (countSeconds % 1000 == 0) {
                println("Turn second: ${countSeconds / 1000}")
                if (isBattleActive && person1.boolStats.isAlive.get() && person2.boolStats.isAlive.get()) person1.onAttack(person2)
                if (isBattleActive && person1.boolStats.isAlive.get() && person2.boolStats.isAlive.get()) person2.onAttack(person1)
                if (!person1.boolStats.isAlive.get() || !person2.boolStats.isAlive.get()) {
                    isBattleActive = false
                    break
                }
                println("Person1: ${person1.battleStats.Health} Person2: ${person2.battleStats.Health}")
            }
            countSeconds += 100
            delay(100)
        }
    }
}