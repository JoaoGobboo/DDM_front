package com.example.d_dmaster

class ValidadorDePersonagem {

    fun validarDistribuicaoAtributos(personagem: Personagem, pontosDistribuidos: Int, totalPontos: Int): Boolean {
        // Verifica se os pontos distribuídos não excedem o total permitido
        if (pontosDistribuidos > totalPontos) {
            println("Erro: Você distribuiu mais pontos do que o permitido.")
            return false
        }

        // Verifica se algum atributo é negativo
        if (personagem.forca < 0 || personagem.destreza < 0 || personagem.constituicao < 0 ||
            personagem.inteligencia < 0 || personagem.sabedoria < 0 || personagem.carisma < 0) {
            println("Erro: Os atributos não podem ser negativos.")
            return false
        }

        return true
    }

    fun validarNome(nome: String): Boolean {
        if (nome.isBlank()) {
            println("Erro: O nome não pode estar vazio.")
            return false
        }
        return true
    }

    fun validarAtributos(
        forca: Int,
        destreza: Int,
        constituicao: Int,
        inteligencia: Int,
        sabedoria: Int,
        carisma: Int
    ): Boolean {
        val atributos = listOf(forca, destreza, constituicao, inteligencia, sabedoria, carisma)
        if (atributos.any { it < 8 }) {
            println("Erro: Todos os atributos devem ser pelo menos 8.")
            return false
        }
        return true
    }

    fun validarSomaTotalAtributos(
        forca: Int,
        destreza: Int,
        constituicao: Int,
        inteligencia: Int,
        sabedoria: Int,
        carisma: Int
    ): Boolean {
        // Calcula a soma total dos atributos
        val somaTotal = forca + destreza + constituicao + inteligencia + sabedoria + carisma

        // Verifica se a soma total excede 30
        if (somaTotal > 30) {
            println("Erro: A soma dos atributos não pode exceder 30.")
            return false
        }

        // Verifica se cada atributo está dentro do intervalo permitido (8 a 15)
        if (forca < 8 || forca > 15 ||
            destreza < 8 || destreza > 15 ||
            constituicao < 8 || constituicao > 15 ||
            inteligencia < 8 || inteligencia > 15 ||
            sabedoria < 8 || sabedoria > 15 ||
            carisma < 8 || carisma > 15) {
            println("Erro: Todos os atributos devem estar entre 8 e 15.")
            return false
        }

        return true
    }


    fun validarRaca(raca: Raca): Boolean {
        // Verifica se a raça está definida
        if (raca.nome.isBlank()) {
            println("Erro: A raça deve ser definida.")
            return false
        }

        // Verifica se os bônus da raça são válidos
        if (raca.bonusAtributos.isEmpty()) {
            println("Erro: A raça deve ter bônus de atributos.")
            return false
        }

        return true
    }

    fun validarClasse(classe: Classe?): Boolean {
        // Verifica se a classe está definida
        if (classe == null || classe.nome.isBlank()) {
            println("Erro: A classe deve ser definida.")
            return false
        }

        return true
    }

    fun validarPontosDeVida(personagem: Personagem): Boolean {
        // Verifica se os pontos de vida foram calculados corretamente
        val pontosDeVidaEsperados = 10 + personagem.constituicao
        if (personagem.pontosDeVida != pontosDeVidaEsperados) {
            println("Erro: Os pontos de vida estão incorretos.")
            return false
        }

        return true
    }
}
