package com.example.ddm_front.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ddm_front.Logica.Personagem
import com.example.ddm_front.Logica.Classe
import com.example.ddm_front.Logica.Raca
import com.example.ddm_front.Logica.Atributos // Certifique-se de importar a classe Atributos

@Composable
fun PersonagemForm(
    onPersonagemCadastrado: (Personagem) -> Unit,
    classes: List<Classe>,
    racas: List<Raca>
) {
    var nome by remember { mutableStateOf("") }
    var classeSelecionada by remember { mutableStateOf("") }
    var racaSelecionada by remember { mutableStateOf("") }
    val atributos = Atributos() // Criação da instância de Atributos

    // Função para criar o personagem
    fun criarPersonagem() {
        val novaClasse = classes.find { it.nome.equals(classeSelecionada, ignoreCase = true) }
        val novaRaca = racas.find { it.nome.equals(racaSelecionada, ignoreCase = true) }

        val novoPersonagem = Personagem().apply {
            this.nome = nome
            this.classeId = novaClasse?.id ?: 0 // Armazena o ID da classe
            this.racaId = novaRaca?.id ?: 0 // Armazena o ID da raça
            // Aplica bônus de classe e raça
            if (novaClasse != null) {
                aplicarBonusClasse(novaClasse, atributos) // Passa a classe e atributos
            }
            if (novaRaca != null) {
                aplicarBonusRaca(novaRaca, atributos) // Passa a raça e atributos
            }
            calcularPontosDeVida(atributos) // Passa os atributos para calcular pontos de vida
        }
        onPersonagemCadastrado(novoPersonagem)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Criar Personagem", style = MaterialTheme.typography.titleLarge)

        // Campo para nome
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para seleção de classe
        OutlinedTextField(
            value = classeSelecionada,
            onValueChange = { classeSelecionada = it },
            label = { Text("Classe") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para seleção de raça
        OutlinedTextField(
            value = racaSelecionada,
            onValueChange = { racaSelecionada = it },
            label = { Text("Raça") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Atributos
        AtributosContador(atributos) { novosAtributos ->
            // Atualiza a instância de atributos conforme necessário
            // Lógica para atualizar atributos se necessário
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para criar o personagem
        Button(
            onClick = { criarPersonagem() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Criar Personagem")
        }
    }
}
