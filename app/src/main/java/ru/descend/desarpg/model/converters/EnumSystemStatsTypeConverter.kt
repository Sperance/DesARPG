package ru.descend.desarpg.model.converters

import io.objectbox.converter.PropertyConverter
import ru.descend.desarpg.model.EnumSystemStatsType

class EnumSystemStatsTypeConverter : PropertyConverter<EnumSystemStatsType, String> {
    override fun convertToEntityProperty(databaseValue: String?): EnumSystemStatsType {
        return if (databaseValue == null) {
            EnumSystemStatsType.UNDEFINED
        } else {
            EnumSystemStatsType.entries.find { it.name == databaseValue } ?: EnumSystemStatsType.UNDEFINED
        }
    }

    override fun convertToDatabaseValue(entityProperty: EnumSystemStatsType?): String {
        return entityProperty?.name ?: EnumSystemStatsType.UNDEFINED.name
    }
}