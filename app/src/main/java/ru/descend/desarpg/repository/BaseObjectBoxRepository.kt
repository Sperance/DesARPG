package ru.descend.desarpg.repository

import io.objectbox.Box
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

abstract class BaseObjectBoxRepository<T>(val box: Box<T>) {

    // Метод для добавления или обновления объекта
    fun save(entity: T): Long {
        return box.put(entity)
    }

    // Метод для получения объекта по ID
    fun getById(id: Long): T? {
        return box.get(id)
    }

    // Метод для получения всех объектов
    fun getAll(): List<T> {
        return box.all
    }

    // Метод для удаления объекта
    fun delete(entity: T) {
        box.remove(entity)
    }

    // Метод для удаления объекта по ID
    fun deleteById(id: Long) {
        box.remove(id)
    }

    // Метод для удаления всех объектов
    fun deleteAll() {
        box.removeAll()
    }

    // Метод для добавления элемента в toMany связь
    fun <R> addToRelation(entity: T, relation: ToMany<R>, relatedEntity: R) {
        relation.add(relatedEntity)
        save(entity)
    }

    // Метод для добавления элемента в toMany связь
    fun <R> addToRelation(entity: T, relation: ToMany<R>, relatedEntity: Collection<R>) {
        relation.addAll(relatedEntity)
        save(entity)
    }

    // Метод для удаления элемента из toMany связь
    fun <R> removeFromRelation(entity: T, relation: ToMany<R>, relatedEntity: R) {
        relation.remove(relatedEntity)
        save(entity)
    }

    // Метод для получения всех элементов из toMany связь
    fun <R> getRelationItems(relation: ToMany<R>): List<R> {
        return relation.toList()
    }

    // Метод для установки связанного объекта в toOne связь
    fun <R> setToOne(entity: T, relation: ToOne<R>, relatedEntity: R?) {
        relation.target = relatedEntity
        save(entity)
    }

    // Метод для получения связанного объекта из toOne связь
    fun <R> getToOne(relation: ToOne<R>): R? {
        return relation.target
    }
}