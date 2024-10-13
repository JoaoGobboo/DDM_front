package com.example.ddm_front

import com.example.d_dmaster.Classe
import com.example.d_dmaster.Raca
import com.example.d_dmaster.Personagem

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    // Lista de personagens criados
    private val personagensCriados = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriacaoPersonagemScreen { personagem ->
                // Adiciona o personagem à lista quando criado
                personagensCriados.add(personagem)
            }
        }
    }

    @Composable
    fun CriacaoPersonagemScreen(onPersonagemCriado: (String) -> Unit) {
        var nome by remember { mutableStateOf("") }
        var classe by remember { mutableStateOf(Classe("Guerreiro", mapOf("Forca" to 2))) }
        var raca by remember { mutableStateOf(Raca("Humano", null, mapOf("Forca" to 1))) }

        // Inicializando os atributos com 8
        var forca by remember { mutableStateOf(8) }
        var destreza by remember { mutableStateOf(8) }
        var constituicao by remember { mutableStateOf(8) }
        var inteligencia by remember { mutableStateOf(8) }
        var sabedoria by remember { mutableStateOf(8) }
        var carisma by remember { mutableStateOf(8) }

        val context = LocalContext.current

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Criação de Personagem", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Nome
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Seleção da Classe
                Text("Classe: ${classe.nome}")
                ClassSelection(classes = listOf("Guerreiro", "Mago", "Arqueiro", "Ladino")) { selectedClass ->
                    classe = when (selectedClass) {
                        "Guerreiro" -> Classe("Guerreiro", mapOf("Forca" to 2))
                        "Mago" -> Classe("Mago", mapOf("Inteligencia" to 2))
                        "Arqueiro" -> Classe("Arqueiro", mapOf("Destreza" to 2))
                        "Ladino" -> Classe("Ladino", mapOf("Destreza" to 2))
                        else -> Classe("Guerreiro", mapOf("Forca" to 2))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Seleção da Raça
                Text("Raça: ${raca.nome}")
                ClassSelection(classes = listOf("Humano", "Elfo", "Anão", "Orc")) { selectedRace ->
                    raca = when (selectedRace) {
                        "Humano" -> Raca("Humano", null, mapOf("Forca" to 1))
                        "Elfo" -> Raca("Elfo", null, mapOf("Destreza" to 1))
                        "Anão" -> Raca("Anão", null, mapOf("Constituicao" to 1))
                        "Orc" -> Raca("Orc", null, mapOf("Forca" to 2))
                        else -> Raca("Humano", null, mapOf("Forca" to 1))
                    }
                }

                // Contadores de Atributos
                AtributoComContador("Força", forca, { if (forca < 15) forca++ }, { if (forca > 8) forca-- })
                AtributoComContador("Destreza", destreza, { if (destreza < 15) destreza++ }, { if (destreza > 8) destreza-- })
                AtributoComContador("Constituição", constituicao, { if (constituicao < 15) constituicao++ }, { if (constituicao > 8) constituicao-- })
                AtributoComContador("Inteligência", inteligencia, { if (inteligencia < 15) inteligencia++ }, { if (inteligencia > 8) inteligencia-- })
                AtributoComContador("Sabedoria", sabedoria, { if (sabedoria < 15) sabedoria++ }, { if (sabedoria > 8) sabedoria-- })
                AtributoComContador("Carisma", carisma, { if (carisma < 15) carisma++ }, { if (carisma > 8) carisma-- })

                // Criar Personagem
                Button(onClick = {
                    val personagem = Personagem().apply {
                        this.nome = nome
                        this.classe = classe
                        this.raca = raca

                        // Aplica os bônus de raça e classe
                        aplicarBonusClasse()
                        aplicarBonusRaca()
                        calcularPontosDeVida() // Calcula os pontos de vida após aplicar bônus
                    }

                    // Formata os dados do personagem
                    val personagemInfo = "Nome: ${personagem.nome}, Classe: ${personagem.classe?.nome}, Raça: ${personagem.raca?.nome}, " +
                            "Força: ${personagem.forca}, Destreza: ${personagem.destreza}, Constituição: ${personagem.constituicao}, " +
                            "Inteligência: ${personagem.inteligencia}, Sabedoria: ${personagem.sabedoria}, Carisma: ${personagem.carisma}, " +
                            "Pontos de Vida: ${personagem.pontosDeVida}"

                    onPersonagemCriado(personagemInfo) // Adiciona à lista de personagens criados
                    Toast.makeText(context, "Personagem criado", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Criar Personagem")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão para listar personagens
                Button(onClick = {
                    val intent = Intent(context, ListaPersonagensActivity::class.java).apply {
                        putExtra("personagens", personagensCriados.toList().toTypedArray())
                    }
                    context.startActivity(intent)
                }) {
                    Text("Listar Personagens")
                }
            }
        }
    }

    @Composable
    fun AtributoComContador(nome: String, valor: Int, incrementar: () -> Unit, decrementar: () -> Unit) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(nome)
            Row {
                Button(onClick = decrementar) {
                    Text("-")
                }
                Text(valor.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                Button(onClick = incrementar) {
                    Text("+")
                }
            }
        }
    }

    @Composable
    fun ClassSelection(classes: List<String>, onSelect: (String) -> Unit) {
        var selectedClass by remember { mutableStateOf("Selecione uma opção") }
        var showClassOptions by remember { mutableStateOf(false) }

        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedButton(onClick = { showClassOptions = !showClassOptions }) {
                Text(text = selectedClass)
            }

            if (showClassOptions) {
                Column {
                    classes.forEach { className ->
                        Button(
                            onClick = {
                                selectedClass = className
                                onSelect(className)
                                showClassOptions = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(className)
                        }
                    }
                }
            }
        }
    }
}
