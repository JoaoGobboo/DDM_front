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
    private fun calcularCustoPontos(valorDesejado: Int, valorAtual: Int = 8): Int {
        var custo = 0
        for (i in valorAtual until valorDesejado) {
            custo += if (i >= 13) 2 else 1
        }
        return custo
    }

    fun setAtributo(nome: String, valor: Int) {
        if (valor < 8 || valor > 15) {
            throw IllegalArgumentException("Valor deve estar entre 8 e 15")
        }

        val valorAtual = atributosValores.getOrDefault(nome, 8)
        val custoPontos = if (valor > valorAtual) {
            calcularCustoPontos(valor, valorAtual)
        } else {
            -calcularCustoPontos(valorAtual, valor)
        }

        if (pontos - custoPontos < 0) {
            throw IllegalArgumentException("Pontos insuficientes para esta alteração")
        }

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

    // Função para calcular o modificador de um atributo (ex: Constituição)
    fun getModificador(nome: String): Int {
        val valor = getAtributo(nome)
        return (valor - 10) / 2
    }
}
