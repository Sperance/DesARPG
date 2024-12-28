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

interface IntBaseValues {
    val listenerBeforeBattle: ItemListener
        get() = ItemListener("Начало боя")
    fun onBeforeBattle(myUnit: Mob, enemy: Mob) { listenerBeforeBattle.invoke() }

    val listenerDoDamage: ItemListener
        get() = ItemListener("Нанесение урона")
    fun onDoDamage(myUnit: Mob, enemy: Mob) { listenerDoDamage.invoke() }

    val listenerTakeDamage: ItemListener
        get() = ItemListener("Получение урона")
    fun onTakeDamage(myUnit: Mob, enemy: Mob) { listenerTakeDamage.invoke() }

    val listenerAfterBattle: ItemListener
        get() = ItemListener("Конец боя")
    fun onAfterBattle(myUnit: Mob, enemy: Mob) { listenerAfterBattle.invoke() }
}