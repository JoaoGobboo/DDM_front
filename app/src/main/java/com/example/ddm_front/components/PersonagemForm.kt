package com.example.ddm_front.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Personagem

@Composable
fun PersonagemForm(
    onSavePersonagem: (Personagem, Atributos) -> Unit,
    onListPersonagens: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf("") }
    var atributos by remember { mutableStateOf(Atributos()) }

    // Estados para cada atributo
    var forca by remember { mutableStateOf(8) }
    var destreza by remember { mutableStateOf(8) }
    var constituicao by remember { mutableStateOf(8) }
    var inteligencia by remember { mutableStateOf(8) }
    var sabedoria by remember { mutableStateOf(8) }
    var carisma by remember { mutableStateOf(8) }

    // Estado para mensagens de erro
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "Criar Personagem",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo Nome
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Personagem") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Pontos Disponíveis
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = "Pontos Disponíveis: ${atributos.getPontosDisponiveis()}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Campos de Atributos
        AtributoField(
            label = "Força",
            value = forca,
            onIncrement = {
                try {
                    val novosAtributos = Atributos().apply {
                        setAtributo("Força", forca + 1)
                        setAtributo("Destreza", destreza)
                        setAtributo("Constituição", constituicao)
                        setAtributo("Inteligência", inteligencia)
                        setAtributo("Sabedoria", sabedoria)
                        setAtributo("Carisma", carisma)
                    }
                    atributos = novosAtributos
                    forca++
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            },
            onDecrement = {
                if (forca > 8) {
                    try {
                        val novosAtributos = Atributos().apply {
                            setAtributo("Força", forca - 1)
                            setAtributo("Destreza", destreza)
                            setAtributo("Constituição", constituicao)
                            setAtributo("Inteligência", inteligencia)
                            setAtributo("Sabedoria", sabedoria)
                            setAtributo("Carisma", carisma)
                        }
                        atributos = novosAtributos
                        forca--
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            }
        )

        AtributoField(
            label = "Destreza",
            value = destreza,
            onIncrement = {
                try {
                    val novosAtributos = Atributos().apply {
                        setAtributo("Força", forca)
                        setAtributo("Destreza", destreza + 1)
                        setAtributo("Constituição", constituicao)
                        setAtributo("Inteligência", inteligencia)
                        setAtributo("Sabedoria", sabedoria)
                        setAtributo("Carisma", carisma)
                    }
                    atributos = novosAtributos
                    destreza++
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            },
            onDecrement = {
                if (destreza > 8) {
                    try {
                        val novosAtributos = Atributos().apply {
                            setAtributo("Força", forca)
                            setAtributo("Destreza", destreza - 1)
                            setAtributo("Constituição", constituicao)
                            setAtributo("Inteligência", inteligencia)
                            setAtributo("Sabedoria", sabedoria)
                            setAtributo("Carisma", carisma)
                        }
                        atributos = novosAtributos
                        destreza--
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            }
        )

        AtributoField(
            label = "Constituição",
            value = constituicao,
            onIncrement = {
                try {
                    val novosAtributos = Atributos().apply {
                        setAtributo("Força", forca)
                        setAtributo("Destreza", destreza)
                        setAtributo("Constituição", constituicao + 1)
                        setAtributo("Inteligência", inteligencia)
                        setAtributo("Sabedoria", sabedoria)
                        setAtributo("Carisma", carisma)
                    }
                    atributos = novosAtributos
                    constituicao++
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            },
            onDecrement = {
                if (constituicao > 8) {
                    try {
                        val novosAtributos = Atributos().apply {
                            setAtributo("Força", forca)
                            setAtributo("Destreza", destreza)
                            setAtributo("Constituição", constituicao - 1)
                            setAtributo("Inteligência", inteligencia)
                            setAtributo("Sabedoria", sabedoria)
                            setAtributo("Carisma", carisma)
                        }
                        atributos = novosAtributos
                        constituicao--
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            }
        )

        AtributoField(
            label = "Inteligência",
            value = inteligencia,
            onIncrement = {
                try {
                    val novosAtributos = Atributos().apply {
                        setAtributo("Força", forca)
                        setAtributo("Destreza", destreza)
                        setAtributo("Constituição", constituicao)
                        setAtributo("Inteligência", inteligencia + 1)
                        setAtributo("Sabedoria", sabedoria)
                        setAtributo("Carisma", carisma)
                    }
                    atributos = novosAtributos
                    inteligencia++
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            },
            onDecrement = {
                if (inteligencia > 8) {
                    try {
                        val novosAtributos = Atributos().apply {
                            setAtributo("Força", forca)
                            setAtributo("Destreza", destreza)
                            setAtributo("Constituição", constituicao)
                            setAtributo("Inteligência", inteligencia - 1)
                            setAtributo("Sabedoria", sabedoria)
                            setAtributo("Carisma", carisma)
                        }
                        atributos = novosAtributos
                        inteligencia--
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            }
        )

        AtributoField(
            label = "Sabedoria",
            value = sabedoria,
            onIncrement = {
                try {
                    val novosAtributos = Atributos().apply {
                        setAtributo("Força", forca)
                        setAtributo("Destreza", destreza)
                        setAtributo("Constituição", constituicao)
                        setAtributo("Inteligência", inteligencia)
                        setAtributo("Sabedoria", sabedoria + 1)
                        setAtributo("Carisma", carisma)
                    }
                    atributos = novosAtributos
                    sabedoria++
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            },
            onDecrement = {
                if (sabedoria > 8) {
                    try {
                        val novosAtributos = Atributos().apply {
                            setAtributo("Força", forca)
                            setAtributo("Destreza", destreza)
                            setAtributo("Constituição", constituicao)
                            setAtributo("Inteligência", inteligencia)
                            setAtributo("Sabedoria", sabedoria - 1)
                            setAtributo("Carisma", carisma)
                        }
                        atributos = novosAtributos
                        sabedoria--
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            }
        )

        AtributoField(
            label = "Carisma",
            value = carisma,
            onIncrement = {
                try {
                    val novosAtributos = Atributos().apply {
                        setAtributo("Força", forca)
                        setAtributo("Destreza", destreza)
                        setAtributo("Constituição", constituicao)
                        setAtributo("Inteligência", inteligencia)
                        setAtributo("Sabedoria", sabedoria)
                        setAtributo("Carisma", carisma + 1)
                    }
                    atributos = novosAtributos
                    carisma++
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            },
            onDecrement = {
                if (carisma > 8) {
                    try {
                        val novosAtributos = Atributos().apply {
                            setAtributo("Força", forca)
                            setAtributo("Destreza", destreza)
                            setAtributo("Constituição", constituicao)
                            setAtributo("Inteligência", inteligencia)
                            setAtributo("Sabedoria", sabedoria)
                            setAtributo("Carisma", carisma - 1)
                        }
                        atributos = novosAtributos
                        carisma--
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            }
        )

        // Mensagem de erro
        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Botão Salvar
        Button(
            onClick = {
                if (nome.isNotEmpty() && atributos.validarAtributos()) {
                    val personagem = Personagem().apply {
                        this.nome = nome
                    }
                    onSavePersonagem(personagem, atributos)
                } else {
                    errorMessage = "Preencha todos os campos corretamente"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Salvar Personagem")
        }

        // Botão Listar Personagens
        OutlinedButton(
            onClick = onListPersonagens,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Listar Personagens")
        }
    }
}

@Composable
private fun AtributoField(
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onDecrement,
            modifier = Modifier.width(48.dp)
        ) {
            Text("-")
        }

        Text(
            text = value.toString(),
            modifier = Modifier.width(48.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onIncrement,
            modifier = Modifier.width(48.dp)
        ) {
            Text("+")
        }
    }
}