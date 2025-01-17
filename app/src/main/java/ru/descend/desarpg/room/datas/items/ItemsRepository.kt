package ru.descend.desarpg.room.datas.items

import ru.descend.desarpg.logic.EnumItemType

class ItemsRepository(private val dao: DaoItems) {
    val allItems = dao.getAllLiveData()

    fun insert(note: RoomItems) {
        val findedItem = dao.getAll().find { it.name == note.name && note.type == EnumItemType.ITEM }
        if (findedItem != null) {
            findedItem.count++
            update(findedItem)
        } else {
            dao.insert(note)
        }
    }

    fun update(note: RoomItems) {
        dao.update(note)
    }

    fun delete(note: RoomItems) {
        dao.delete(note)
    }

    fun deleteAll() {
        dao.deleteAll()
    }
}