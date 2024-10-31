package com.example.ddm_front.Logica

import Atributos
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "personagem",
    foreignKeys = [
        ForeignKey(
            entity = Raca::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("racaId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Atributos::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("atributosId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class Personagem {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var nome: String = ""
    var classeId: Int = 0 // Armazena o ID da classe
    var racaId: Int = 0 // Referência para a Raca
    var atributosId: Int = 0 // Referência para Atributos
    var pontosDeVida: Int = 0

    // Aplica bônus baseados na classe do personagem
    fun aplicarBonusClasse(classe: Classe, atributos: Atributos) {
        when (classe.nome) {
            "Arqueiro" -> atributos.destreza += 3
            "Mago" -> atributos.inteligencia += 3
            "Guerreiro" -> atributos.forca += 3
            // Adicione outras classes conforme necessário
        }
    }

    // Aplica bônus raciais
    fun aplicarBonusRaca(raca: Raca?, atributos: Atributos) {
        raca?.aplicarBonus(atributos)
    }

    // Calcula pontos de vida baseando-se na constituição
    fun calcularPontosDeVida(atributos: Atributos) {
        pontosDeVida = 10 + (atributos.constituicao - 10) / 2 // Modificador de Constituição
    }
}
