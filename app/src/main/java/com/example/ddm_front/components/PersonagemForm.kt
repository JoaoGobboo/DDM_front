package com.example.ddm_front.UI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Classe
import com.example.ddm_front.Logica.Personagem
import com.example.ddm_front.Logica.Raca


@Composable
fun AtributoField(
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onDecrement,
            enabled = value > 8,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text("-")
        }

        Text(
            text = value.toString(),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Button(
            onClick = onIncrement,
            enabled = value < 15,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text("+")
        }
    }
}

@Composable
fun PersonagemForm(
    onSavePersonagem: (Personagem, Atributos) -> Unit,
    onListPersonagens: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf("") }
    var atributos by remember { mutableStateOf(Atributos()) }

    // Estado para Classe e Raça
    var classeSelecionada by remember { mutableStateOf<Classe?>(null) }
    var racaSelecionada by remember { mutableStateOf<Raca?>(null) }

    // Estado para mensagem de erro
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Lista de Classes
    val classes = listOf(
        Classe(nome = "Guerreiro", bonusAtributos = mapOf("Força" to 2)),
        Classe(nome = "Mago", bonusAtributos = mapOf("Inteligência" to 2)),
        Classe(nome = "Ladino", bonusAtributos = mapOf("Destreza" to 2)),
        Classe(nome = "Clérigo", bonusAtributos = mapOf("Sabedoria" to 2))
    )

    // Lista de Raças
    val racas = listOf(
        Raca(nome = "Humano", bonusAtributos = mapOf("Força" to 1, "Destreza" to 1)),
        Raca(nome = "Elfo", bonusAtributos = mapOf("Destreza" to 2)),
        Raca(nome = "Anão", bonusAtributos = mapOf("Constituição" to 2)),
        Raca(nome = "Meio-Elfo", bonusAtributos = mapOf("Carisma" to 1, "Destreza" to 1))
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Criar Personagem",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Personagem") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Pontos Disponíveis: ${atributos.getPontosDisponiveis()}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Seleção de Classe
        Text("Selecione a Classe:", style = MaterialTheme.typography.titleMedium)
        classes.forEach { classe ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (classeSelecionada?.nome == classe.nome),
                    onClick = {
                        classeSelecionada = classe
                        // Aplicar bônus da classe
                        try {
                            classe.aplicarBonus(atributos)
                        } catch (e: IllegalArgumentException) {
                            errorMessage = e.message
                        }
                    }
                )
                Text(classe.nome, modifier = Modifier.padding(start = 8.dp))
            }
        }

        // Seleção de Raça
        Text("Selecione a Raça:", style = MaterialTheme.typography.titleMedium)
        racas.forEach { raca ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (racaSelecionada?.nome == raca.nome),
                    onClick = {
                        racaSelecionada = raca
                        // Aplicar bônus da raça
                        try {
                            raca.aplicarBonus(atributos)
                        } catch (e: IllegalArgumentException) {
                            errorMessage = e.message
                        }
                    }
                )
                Text(raca.nome, modifier = Modifier.padding(start = 8.dp))
            }
        }

        // Campos de Atributos
        listOf(
            "Força", "Destreza", "Constituição",
            "Inteligência", "Sabedoria", "Carisma"
        ).forEach { atributoNome ->
            AtributoField(
                label = atributoNome,
                value = atributos.getAtributo(atributoNome),
                onIncrement = {
                    try {
                        atributos.setAtributo(atributoNome, atributos.getAtributo(atributoNome) + 1)
                    } catch (e: IllegalArgumentException) {
                        errorMessage = e.message
                    }
                },
                onDecrement = {
                    try {
                        atributos.setAtributo(atributoNome, atributos.getAtributo(atributoNome) - 1)
                    } catch (e: IllegalArgumentException) {
                        errorMessage = e.message
                    }
                }
            )
        }

        // Mensagem de erro
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = {
                if (nome.isEmpty()) {
                    errorMessage = "Digite um nome para o personagem"
                    return@Button
                }
                if (classeSelecionada == null) {
                    errorMessage = "Selecione uma classe"
                    return@Button
                }
                if (racaSelecionada == null) {
                    errorMessage = "Selecione uma raça"
                    return@Button
                }
                if (!atributos.validarAtributos()) {
                    errorMessage = "Atributos inválidos"
                    return@Button
                }

                val personagem = Personagem(nome = nome)
                onSavePersonagem(personagem, atributos)
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Personagem")
        }

        OutlinedButton(
            onClick = onListPersonagens,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Listar Personagens")
        }
    }
}