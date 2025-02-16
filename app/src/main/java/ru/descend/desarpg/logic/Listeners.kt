package ru.descend.desarpg.logic

class ItemListener(val name: String) {
    private val listenersArray: ArrayList<() -> Unit> = ArrayList()
    fun clearListeners() {
        listenersArray.clear()
    }
    fun addListener(body: () -> Unit) {
        listenersArray.add(body)
    }
    fun invoke() {
        listenersArray.forEach {
            it.invoke()
        }
    }
}

//interface IntBaseValues {
//    val listenerBeforeBattle: ItemListener
//        get() = ItemListener("Начало боя")
//    fun onBeforeBattle(myUnit: RoomMobs, enemy: RoomMobs) { listenerBeforeBattle.invoke() }
//
//    val listenerDoDamage: ItemListener
//        get() = ItemListener("Нанесение урона")
//    fun onDoDamage(myUnit: RoomMobs, enemy: RoomMobs) { listenerDoDamage.invoke() }
//
//    val listenerTakeDamage: ItemListener
//        get() = ItemListener("Получение урона")
//    fun onTakeDamage(myUnit: RoomMobs, enemy: RoomMobs) { listenerTakeDamage.invoke() }
//
//    val listenerAfterBattle: ItemListener
//        get() = ItemListener("Конец боя")
//    fun onAfterBattle(myUnit: RoomMobs, enemy: RoomMobs) { listenerAfterBattle.invoke() }
//}