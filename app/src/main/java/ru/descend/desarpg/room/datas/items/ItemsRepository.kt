package ru.descend.desarpg.room.datas.items

class ItemsRepository(private val dao: DaoItems) {
    val allItems = dao.getAllLiveData()

    fun insert(note: RoomItems) {
        dao.insert(note)
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