package com.example.ddm_front.Logica

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "atributos")
data class Atributos(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var pontos: Int = 27,

    @TypeConverters(MapTypeConverter::class)
    var atributosValores: MutableMap<String, Int> = mutableMapOf()
) {
    // Função para calcular o custo de um atributo
    private fun calcularCustoPontos(valorDesejado: Int, valorAtual: Int = 8): Int {
        var custo = 0
        for (i in valorAtual until valorDesejado) {
            custo += if (i >= 13) 2 else 1
        }
        return custo
    }

    fun setAtributo(nome: String, valor: Int) {
        // Verifica se o valor está dentro dos limites permitidos
        if (valor < 8 || valor > 15) {
            throw IllegalArgumentException("Valor deve estar entre 8 e 15")
        }

        // Obtém o valor atual do atributo (ou 8 se não existir)
        val valorAtual = atributosValores.getOrDefault(nome, 8)

        // Calcula o custo da alteração
        val custoPontos = if (valor > valorAtual) {
            calcularCustoPontos(valor, valorAtual)
        } else {
            -calcularCustoPontos(valorAtual, valor)
        }

        // Verifica se há pontos suficientes
        if (pontos - custoPontos < 0) {
            throw IllegalArgumentException("Pontos insuficientes para esta alteração")
        }

        // Aplica a alteração
        pontos -= custoPontos
        atributosValores[nome] = valor
    }

    fun getAtributo(nome: String): Int {
        return atributosValores.getOrDefault(nome, 8)
    }

    fun getPontosDisponiveis(): Int {
        return pontos
    }

    fun resetAtributo(nome: String) {
        val valorAtual = atributosValores.getOrDefault(nome, 8)
        if (valorAtual > 8) {
            pontos += calcularCustoPontos(valorAtual, 8)
            atributosValores.remove(nome)
        }
    }

    fun validarAtributos(): Boolean {
        return atributosValores.all { (_, valor) -> valor in 8..15 }
    }
}