package com.example.ddm_front.Logica

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "raca")
@TypeConverters(MapTypeConverter::class)
data class Raca(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val subRaca: String? = null,
    val bonusAtributos: Map<String, Int>
) {
    fun aplicarBonus(atributos: Atributos) {
        bonusAtributos.forEach { (atributo, bonus) ->
            val valorAtual = atributos.getAtributo(atributo)
            atributos.setAtributo(atributo, valorAtual + bonus)
        }
    }

    fun getBonusString(): String {
        return bonusAtributos.entries.joinToString(", ") { "${it.key}: ${it.value}" }
    }
}