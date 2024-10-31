package com.example.ddm_front.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ddm_front.Logica.Atributos

@Composable
fun AtributosContador(
    atributos: Atributos,
    onAtributosChange: (Atributos) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Atributos", style = MaterialTheme.typography.titleLarge)

        AtributoItem(
            nome = "Força",
            valor = atributos.forca,
            onIncrement = {
                val novoValor = atributos.forca + 1
                try {
                    atributos.setAtributo("forca", novoValor)
                    onAtributosChange(atributos)
                } catch (e: IllegalArgumentException) {
                    // Tratar exceção se necessário (exibir mensagem ou log)
                }
            },
            onDecrement = {
                val novoValor = atributos.forca - 1
                if (novoValor >= 8) {
                    try {
                        atributos.setAtributo("forca", novoValor)
                        onAtributosChange(atributos)
                    } catch (e: IllegalArgumentException) {
                        // Tratar exceção se necessário (exibir mensagem ou log)
                    }
                }
            }
        )

        AtributoItem(
            nome = "Destreza",
            valor = atributos.destreza,
            onIncrement = {
                val novoValor = atributos.destreza + 1
                try {
                    atributos.setAtributo("destreza", novoValor)
                    onAtributosChange(atributos)
                } catch (e: IllegalArgumentException) {
                    // Tratar exceção se necessário (exibir mensagem ou log)
                }
            },
            onDecrement = {
                val novoValor = atributos.destreza - 1
                if (novoValor >= 8) {
                    try {
                        atributos.setAtributo("destreza", novoValor)
                        onAtributosChange(atributos)
                    } catch (e: IllegalArgumentException) {
                        // Tratar exceção se necessário (exibir mensagem ou log)
                    }
                }
            }
        )

        AtributoItem(
            nome = "Constituição",
            valor = atributos.constituicao,
            onIncrement = {
                val novoValor = atributos.constituicao + 1
                try {
                    atributos.setAtributo("constituição", novoValor)
                    onAtributosChange(atributos)
                } catch (e: IllegalArgumentException) {
                    // Tratar exceção se necessário (exibir mensagem ou log)
                }
            },
            onDecrement = {
                val novoValor = atributos.constituicao - 1
                if (novoValor >= 8) {
                    try {
                        atributos.setAtributo("constituição", novoValor)
                        onAtributosChange(atributos)
                    } catch (e: IllegalArgumentException) {
                        // Tratar exceção se necessário (exibir mensagem ou log)
                    }
                }
            }
        )

        AtributoItem(
            nome = "Inteligência",
            valor = atributos.inteligencia,
            onIncrement = {
                val novoValor = atributos.inteligencia + 1
                try {
                    atributos.setAtributo("inteligência", novoValor)
                    onAtributosChange(atributos)
                } catch (e: IllegalArgumentException) {
                    // Tratar exceção se necessário (exibir mensagem ou log)
                }
            },
            onDecrement = {
                val novoValor = atributos.inteligencia - 1
                if (novoValor >= 8) {
                    try {
                        atributos.setAtributo("inteligência", novoValor)
                        onAtributosChange(atributos)
                    } catch (e: IllegalArgumentException) {
                        // Tratar exceção se necessário (exibir mensagem ou log)
                    }
                }
            }
        )

        AtributoItem(
            nome = "Sabedoria",
            valor = atributos.sabedoria,
            onIncrement = {
                val novoValor = atributos.sabedoria + 1
                try {
                    atributos.setAtributo("sabedoria", novoValor)
                    onAtributosChange(atributos)
                } catch (e: IllegalArgumentException) {
                    // Tratar exceção se necessário (exibir mensagem ou log)
                }
            },
            onDecrement = {
                val novoValor = atributos.sabedoria - 1
                if (novoValor >= 8) {
                    try {
                        atributos.setAtributo("sabedoria", novoValor)
                        onAtributosChange(atributos)
                    } catch (e: IllegalArgumentException) {
                        // Tratar exceção se necessário (exibir mensagem ou log)
                    }
                }
            }
        )

        AtributoItem(
            nome = "Carisma",
            valor = atributos.carisma,
            onIncrement = {
                val novoValor = atributos.carisma + 1
                try {
                    atributos.setAtributo("carisma", novoValor)
                    onAtributosChange(atributos)
                } catch (e: IllegalArgumentException) {
                    // Tratar exceção se necessário (exibir mensagem ou log)
                }
            },
            onDecrement = {
                val novoValor = atributos.carisma - 1
                if (novoValor >= 8) {
                    try {
                        atributos.setAtributo("carisma", novoValor)
                        onAtributosChange(atributos)
                    } catch (e: IllegalArgumentException) {
                        // Tratar exceção se necessário (exibir mensagem ou log)
                    }
                }
            }
        )

        // Exibir pontos disponíveis
        Text(text = "Pontos disponíveis: ${atributos.getPontosDisponiveis()}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun AtributoItem(
    nome: String,
    valor: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$nome: $valor", modifier = Modifier.weight(1f))

        Button(onClick = onDecrement) {
            Text("-")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = onIncrement) {
            Text("+")
        }
    }
}
