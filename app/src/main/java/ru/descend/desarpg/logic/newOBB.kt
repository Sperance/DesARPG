package ru.descend.desarpg.logic

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
open class NewOBB {
    @Id var id: Long = 0
    var name: String = ""
    var age: Int = 1

    override fun toString(): String {
        return "NewOBB(id=$id, name='$name', age=$age)"
    }
}

@Entity
class NewObbAbs : NewOBB() {
    var newValue: Int = 1

    override fun toString(): String {
        return "NewObbAbs(newValue=$newValue, id=$id, name=$name, age=$age)"
    }
}