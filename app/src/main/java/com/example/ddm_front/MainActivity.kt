package com.example.d_dmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.d_dmaster.ui.theme.DDMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DDMasterTheme {
                TelaCriacaoPersonagem()
            }
        }
    }
}

@Composable
fun TelaCriacaoPersonagem() {
    var nomePersonagem by remember { mutableStateOf("") }
    val personagem = remember { Personagem() }
    var classeSelecionada by remember { mutableStateOf<Classe?>(null) }
    var racaSelecionada by remember { mutableStateOf<Raca?>(null) }
    var pontosRestantes by remember { mutableStateOf(27) }
    var personagemCriado by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val classes = listOf(
        Classe("Arqueiro", mapOf("Destreza" to 3)),
        Classe("Mago", mapOf("Inteligência" to 3)),
        Classe("Guerreiro", mapOf("Força" to 3))
    )

    val racas = listOf(
        Raca("Humano", bonusAtributos = mapOf("Forca" to 1, "Destreza" to 1, "Constituicao" to 1, "Inteligencia" to 1, "Sabedoria" to 1, "Carisma" to 1)),
        Raca("Elfo", bonusAtributos = mapOf("Destreza" to 2, "Inteligencia" to 1)),
        Raca("Anão", bonusAtributos = mapOf("Constituicao" to 2, "Forca" to 2))
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(text = "Criação de Personagem", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nomePersonagem,
            onValueChange = { nomePersonagem = it },
            label = { Text(text = "Nome do Personagem") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Selecione a Classe:")
        classes.forEach { classe ->
            Row {
                RadioButton(
                    selected = classe == classeSelecionada,
                    onClick = { classeSelecionada = classe }
                )
                Text(text = classe.nome)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Selecione a Raça:")
        racas.forEach { raca ->
            Row {
                RadioButton(
                    selected = raca == racaSelecionada,
                    onClick = { racaSelecionada = raca }
                )
                Text(text = raca.nome)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Pontos restantes: $pontosRestantes")

        // Função para atualizar atributos
        fun atualizarAtributo(
            atributo: (Int) -> Unit,
            valorAtual: Int,
            operacao: (Int) -> Int
        ) {
            val novoValor = operacao(valorAtual)
            val custoAtual = calcularCustoPontos(valorAtual)
            val custoNovo = calcularCustoPontos(novoValor)
            val custoTotal = custoNovo - custoAtual

            if (pontosRestantes >= custoTotal && novoValor in 8..15) {
                atributo(novoValor)
                pontosRestantes -= custoTotal
            }
        }

        AtributoField("Força", personagem.forca, pontosRestantes,
            onIncrement = { atualizarAtributo({ personagem.forca = it }, personagem.forca) { it + 1 } },
            onDecrement = { atualizarAtributo({ personagem.forca = it }, personagem.forca) { it - 1 } }
        )

        AtributoField("Destreza", personagem.destreza, pontosRestantes,
            onIncrement = { atualizarAtributo({ personagem.destreza = it }, personagem.destreza) { it + 1 } },
            onDecrement = { atualizarAtributo({ personagem.destreza = it }, personagem.destreza) { it - 1 } }
        )

        AtributoField("Constituição", personagem.constituicao, pontosRestantes,
            onIncrement = { atualizarAtributo({ personagem.constituicao = it }, personagem.constituicao) { it + 1 } },
            onDecrement = { atualizarAtributo({ personagem.constituicao = it }, personagem.constituicao) { it - 1 } }
        )

        AtributoField("Inteligência", personagem.inteligencia, pontosRestantes,
            onIncrement = { atualizarAtributo({ personagem.inteligencia = it }, personagem.inteligencia) { it + 1 } },
            onDecrement = { atualizarAtributo({ personagem.inteligencia = it }, personagem.inteligencia) { it - 1 } }
        )

        AtributoField("Sabedoria", personagem.sabedoria, pontosRestantes,
            onIncrement = { atualizarAtributo({ personagem.sabedoria = it }, personagem.sabedoria) { it + 1 } },
            onDecrement = { atualizarAtributo({ personagem.sabedoria = it }, personagem.sabedoria) { it - 1 } }
        )

        AtributoField("Carisma", personagem.carisma, pontosRestantes,
            onIncrement = { atualizarAtributo({ personagem.carisma = it }, personagem.carisma) { it + 1 } },
            onDecrement = { atualizarAtributo({ personagem.carisma = it }, personagem.carisma) { it - 1 } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (classeSelecionada != null && racaSelecionada != null && nomePersonagem.isNotEmpty()) {
                personagem.nome = nomePersonagem
                personagem.classe = classeSelecionada
                personagem.raca = racaSelecionada
                personagem.aplicarBonusClasse()
                personagem.aplicarBonusRaca()
                personagem.calcularPontosDeVida()
                personagemCriado = true
            }
        }) {
            Text("Criar Personagem")
        }

        if (personagemCriado) {
            Text(text = "Personagem ${personagem.nome} criado com sucesso!")
        }
    }
}

@Composable
fun AtributoField(
    nome: String,
    valor: Int,
    pontosRestantes: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column {
        Text(text = "$nome: $valor")
        Row {
            Button(onClick = onDecrement) {
                Text("-")
            }
            Button(onClick = onIncrement) {
                Text("+")
            }
        }
    }
}

fun calcularCustoPontos(valor: Int): Int {
    return if (valor <= 10) {
        0
    } else {
        (valor - 10)
    }
}
