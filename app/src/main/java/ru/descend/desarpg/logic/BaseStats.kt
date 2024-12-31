package ru.descend.desarpg.logic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

interface StatSerializer<T: PropertyValue> : KSerializer<T> {
    override fun serialize(encoder: Encoder, value: T) {
        val resultString = "${value.get()}:${value.getPercent()}"
        encoder.encodeString(resultString)
    }
    override fun deserialize(decoder: Decoder): T {
        val strValue = decoder.decodeString().split(":")
        val value = strValue[0].toDouble()
        val percent = strValue[1].toDouble()
        val result = (this as T)
        result.set(value)
        result.setPercent(percent)
        return result
    }
}

@Serializable(with = StatHealth::class)
class StatHealth : PropertyValue("Health"), StatSerializer<StatHealth> {
    override val descriptor = PrimitiveSerialDescriptor("StatHealth", PrimitiveKind.STRING)
}

@Serializable(with = StatAttackPhysic::class)
class StatAttackPhysic : PropertyValue("AttackPhysic"), StatSerializer<StatAttackPhysic> {
    override val descriptor = PrimitiveSerialDescriptor("StatAttackPhysic", PrimitiveKind.STRING)
}

@Serializable(with = StatStrength::class)
class StatStrength : PropertyValue("Strength"), StatSerializer<StatStrength> {
    override val descriptor = PrimitiveSerialDescriptor("StatStrength", PrimitiveKind.STRING)
}

@Serializable
class BattleStats(
    @SerialName("hlt") var health: StatHealth = StatHealth(),
    @SerialName("aPh") var attackPhysic: StatAttackPhysic = StatAttackPhysic(),
    @SerialName("str") var strength: StatStrength = StatStrength(),
)