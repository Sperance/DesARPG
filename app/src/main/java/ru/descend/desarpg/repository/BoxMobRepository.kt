package ru.descend.desarpg.repository

import io.objectbox.BoxStore
import ru.descend.desarpg.addPercent
import ru.descend.desarpg.log
import ru.descend.desarpg.model.BaseItem
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobInventory
import ru.descend.desarpg.model.MobMain
import ru.descend.desarpg.model.MobSkillTreeStats
import ru.descend.desarpg.model.MobSystemStats

class BoxMobRepository(currentBox: BoxStore) {

    private val mobMainBox = currentBox.boxFor(MobMain::class.java)
    private var currentMob: MobMain? = null
    fun getCurrentMob(): MobMain {
        if (mobMainBox.isEmpty) {
            log("[CREATED]")

            val newMob = MobMain(name = "Player")

            val newMobBattleStats = MobBattleStats()
            newMobBattleStats.initializeAllStats()
            newMob.mobBattleStats.target = newMobBattleStats

            val newMobSystemStats = MobSystemStats()
            newMobSystemStats.initializeAllStats()
            newMob.mobSystemStats.target = newMobSystemStats

            val newMobSkillTreeStats = MobSkillTreeStats()
            newMobSkillTreeStats.initializeAllStats()
            newMob.mobSkillTreeStats.target = newMobSkillTreeStats

            val newMobInventory = MobInventory()
            newMob.mobInventory.target = newMobInventory

            currentMob = mobMainBox.get(mobMainBox.put(newMob))

            return currentMob!!
        } else {
            log("[ALREADY]")

            currentMob = mobMainBox.get(1)

//            log("INVENTORY ALREADY: ${getCurrentInventory()}")
            return currentMob!!
        }
    }

    private val currentStats = getCurrentMob().mobBattleStats.target
    fun getStockStats() = currentStats
    fun getCurrentStats(): MobBattleStats {

        val newMobStats = MobBattleStats()

        newMobStats.arrayStats.addAll(getStockStats().arrayStats.map { it.copy() })

        val curSkillStats = getCurrentSkillTreeStats()
        curSkillStats.getActiveNodes().forEach { node  ->
            node.arrayStats.forEach { stat ->
                val addingStat = newMobStats.getStockStat(stat.type)
                addingStat.addValue(stat.valueProp.addPercent(node.getCurrentBonusForLevel()))
                addingStat.addPercent(stat.percentProp.addPercent(node.getCurrentBonusForLevel()))
            }
        }

        return newMobStats
    }

    private val currentSystemStats = getCurrentMob().mobSystemStats.target
    fun getCurrentSystemStats(): MobSystemStats {
        return currentSystemStats
    }

    private val currentSkillTreeStats = getCurrentMob().mobSkillTreeStats.target
    fun getCurrentSkillTreeStats(): MobSkillTreeStats {
        return currentSkillTreeStats
    }

    private val currentMobInventory = getCurrentMob().mobInventory.target
    fun getCurrentInventory(): MobInventory {
        return currentMobInventory
    }

    fun test_inventory_mob() {
        val inv = getCurrentInventory()
        log("START INV: $inv")

        val newItem1 = BaseItem(name = "Сфера стража")
        inv.addItem(newItem1)

        log("AFTER ADD 1: $inv")
        inv.addItem(newItem1)

        log("AFTER ADD 2: $inv")

        log("BASE INV: ${getCurrentInventory()}")

        getCurrentInventory().clearInventory()

        log("AFTER CLEAR: ${getCurrentInventory()}")
    }
}