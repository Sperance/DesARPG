package ru.descend.desarpg.model.converters

import io.objectbox.converter.PropertyConverter
import ru.descend.desarpg.model.EnumPropsType

class EnumPropsTypeConverter : PropertyConverter<EnumPropsType, String> {
    override fun convertToEntityProperty(databaseValue: String?): EnumPropsType {
        return if (databaseValue == null) {
            EnumPropsType.UNDEFINED
        } else {
            EnumPropsType.entries.find { it.name == databaseValue } ?: EnumPropsType.UNDEFINED
        }
    }

    override fun convertToDatabaseValue(entityProperty: EnumPropsType?): String {
        return entityProperty?.name ?: EnumPropsType.UNDEFINED.name
    }
}