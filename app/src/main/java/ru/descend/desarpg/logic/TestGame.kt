package ru.descend.desarpg.logic

import org.junit.Test
import ru.descend.desarpg.room.datas.mobs.RoomMobs
import ru.descend.desarpg.room.datas.items.RoomItems
import java.io.File

class TestGame {

    @Test
    fun test_catch_rser() {
        val newEquipItem = RoomItems(name = "Меч Жнеца", rarity = EnumItemRarity.MAGIC, type = EnumItemType.EQUIPMENT)
        newEquipItem.description = "Меч который может Всё"
        newEquipItem.addParam(StockSimpleStatsBool.IsCanSell(true))
        newEquipItem.addParam(StockSimpleStatsBool.IsEquipped(false))
        newEquipItem.addParam(StockSimpleStatsValue.MaxPrefix(5))

        var sell = newEquipItem.getValueResult(StockSimpleStatsValue.MaxPrefix::class)
        println("mod: $sell")

//        newEquipItem.setStockParam(StockSimpleStatsBool.IsCanSell(false))
        newEquipItem.addParam(StockSimpleStatsValue.MaxPrefix(-3))
        sell = newEquipItem.getValueResult(StockSimpleStatsValue.MaxPrefix::class)
        println("mod: $sell")
    }

    fun findClassesInDirectory(directory: String): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        val dir = File(directory)

        // Проверяем, что это директория
        if (dir.exists() && dir.isDirectory) {
            // Проходим по всем файлам и поддиректориям
            dir.walk().forEach { file ->
                if (file.isFile && file.extension == "class") {
                    // Получаем имя класса без расширения
                    val className = file.relativeTo(dir).path
                        .removeSuffix(".class")
                        .replace(File.separatorChar, '.')

                    try {
                        // Загружаем класс по имени
                        val clazz = Class.forName(className)
                        classes.add(clazz)
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace() // Обработка исключений, если класс не найден
                    }
                }
            }
        }

        return classes
    }

    @Test
    fun testBaseStats() {
        val pers1 = RoomMobs(name = "Игрок")
        pers1.battleStats.health.set(100)
        pers1.battleStats.health.setPercent(20)
        pers1.battleStats.strength.set(5)
        println("strength: ${pers1.battleStats.strength}")

        pers1.battleStats.health.prepareInit()
        println("health: ${pers1.battleStats.health}")
//        pers1.battleStats.strength.prepareInit()
        pers1.battleStats.health.removeCurrent(10)
        pers1.battleStats.health.removeCurrent(20)
        pers1.battleStats.health.removeCurrent(50)
        pers1.battleStats.health.removeCurrent(10)

        println("health: ${pers1.battleStats.health}")
        println("strength: ${pers1.battleStats.strength}")
    }

//    @Test
//    fun testSerializationItems() {
//        val item = EquippingItem(name = "Меч света", rarity = EnumItemRarity.DEFAULT)
//        item.paramsBool.add(StockSimpleStatsBool.IsCanSell(false))
//
//        val string = Json.encodeToString(item)
//        println("before: $item")
//        println(string)
//
//        val newItem = Json.decodeFromString<EquippingItem>(string)
//        println("after: $newItem")
//    }

    @Test
    fun test1() {
        val pers1 = RoomMobs(name = "Игрок")
        pers1.battleStats.attackPhysic.set(10)
        pers1.battleStats.health.set(50)
        pers1.battleStats.health.setPercent(10)
        pers1.battleStats.strength.set(5)
        pers1.battleStats.attackPhysic.setPercent(20)
        val pers2 = RoomMobs(name = "Враг")
        pers2.battleStats.attackPhysic.set(8)
        pers2.battleStats.health.set(250)

//        val battle = BattleObject(pers1, pers2)
//        runBlocking {
//            battle.doBattle()
//        }
        println("P1: ${pers1.battleStats.health}")
        println("P2: ${pers2.battleStats.health}")

//        val battle2 = BattleObject(pers1, pers2)
//        runBlocking {
//            battle2.doBattle()
//        }
        println("P1: ${pers1.battleStats.health}")
        println("P2: ${pers2.battleStats.health}")
    }
}