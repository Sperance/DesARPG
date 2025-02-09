package ru.descend.desarpg.logic

import org.junit.Test
import java.io.File

class TestGame {

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

//    @Test
//    fun testBaseStats() {
//        val pers1 = RoomMobs(name = "Игрок")
//        pers1.battleStats.health.setValue(100)
//        pers1.battleStats.health.setPercent(20)
//        pers1.battleStats.strength.setValue(5)
//        println("strength: ${pers1.battleStats.strength}")
//
//        pers1.battleStats.health.prepareInit()
//        println("health: ${pers1.battleStats.health}")
////        pers1.battleStats.strength.prepareInit()
//        pers1.battleStats.health.removeCurrent(10)
//        pers1.battleStats.health.removeCurrent(20)
//        pers1.battleStats.health.removeCurrent(50)
//        pers1.battleStats.health.removeCurrent(10)
//
//        println("health: ${pers1.battleStats.health}")
//        println("strength: ${pers1.battleStats.strength}")
//    }

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
//        val pers1 = RoomMobs(name = "Игрок")
//        pers1.battleStats.attackPhysic.setValue(10)
//        pers1.battleStats.health.setValue(50)
//        pers1.battleStats.health.setPercent(10)
//        pers1.battleStats.strength.setValue(5)
//        pers1.battleStats.attackPhysic.setPercent(20)
//        val pers2 = RoomMobs(name = "Враг")
//        pers2.battleStats.attackPhysic.setValue(8)
//        pers2.battleStats.health.setValue(250)

//        val battle = BattleObject(pers1, pers2)
//        runBlocking {
//            battle.doBattle()
//        }
//        println("P1: ${pers1.battleStats.health}")
//        println("P2: ${pers2.battleStats.health}")

//        val battle2 = BattleObject(pers1, pers2)
//        runBlocking {
//            battle2.doBattle()
//        }
//        println("P1: ${pers1.battleStats.health}")
//        println("P2: ${pers2.battleStats.health}")
    }
}