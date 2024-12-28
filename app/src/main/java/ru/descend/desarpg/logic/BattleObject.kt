package ru.descend.desarpg.logic

import kotlinx.coroutines.delay

class BattleObject(private val person1: Mob, private val person2: Mob) {

    suspend fun doBattle() {
        person1.onBeginBattle(person2)
        person2.onBeginBattle(person1)
        println("Person1: ${person1.battleStats.health} Person2: ${person2.battleStats.health}")
        while (person1.battleStats.health.getCurrent() > 0.0 && person2.battleStats.health.getCurrent() > 0.0) {
            person1.onAttack(person2)
            person1.battleStats.strength.add(1)
            person2.onAttack(person1)
            println("Person1: ${person1.battleStats.health} Person2: ${person2.battleStats.health}")
            delay(1000)
        }
        person1.onEndBattle(person2)
        person2.onEndBattle(person1)
    }
}