package ru.descend.desarpg.model

import io.objectbox.BoxStore

interface IntEntityObjectClass {
    var id: Long

    fun saveToBox()
}