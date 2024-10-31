package com.example.ddm_front.Logica

import Atributos
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "raca")
data class Raca(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var nome: String = "",
    var bonusForca: Int = 0,
    var bonusDestreza: Int = 0,
    var bonusConstituicao: Int = 0,
    var bonusInteligencia: Int = 0,
    var bonusSabedoria: Int = 0,
    var bonusCarisma: Int = 0
) {
    // Método para aplicar bônus da raça aos atributos do personagem
    fun aplicarBonus(atributos: Atributos) {
        atributos.forca += bonusForca
        atributos.destreza += bonusDestreza
        atributos.constituicao += bonusConstituicao
        atributos.inteligencia += bonusInteligencia
        atributos.sabedoria += bonusSabedoria
        atributos.carisma += bonusCarisma
    }
}

