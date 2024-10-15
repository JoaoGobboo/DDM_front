package com.example.ddm_front

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
import com.example.d_dmaster.Classe
import com.example.d_dmaster.Raca
import com.example.d_dmaster.Personagem
import com.example.d_dmaster.ValidadorDePersonagem

class MainActivity : ComponentActivity() {
    // Lista de personagens criados
    private val personagensCriados = mutableStateListOf<String>()

    // Total de pontos disponíveis para distribuição
    private var totalPontos = 27

    // Inicializando os atributos com 8 usando mutableIntStateOf
    private var forca by mutableIntStateOf(8)
    private var destreza by mutableIntStateOf(8)
    private var constituicao by mutableIntStateOf(8)
    private var inteligencia by mutableIntStateOf(8)
    private var sabedoria by mutableIntStateOf(8)
    private var carisma by mutableIntStateOf(8)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriacaoPersonagemScreen(personagensCriados::add)
        }
    }

    @Composable
    fun CriacaoPersonagemScreen(onPersonagemCriado: (String) -> Unit) {
        var nome by remember { mutableStateOf("") }
        var classe by remember { mutableStateOf(Classe("Guerreiro", mapOf("Forca" to 2))) }
        var raca by remember { mutableStateOf(Raca("Humano", null, mapOf("Forca" to 1))) }

        // Exibir bônus de atributos
        var bonusTexto by remember { mutableStateOf("") }

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
                    // Exibir bônus
                    bonusTexto = "Bônus: ${raca.getBonusString()}"
                }

                // Exibir bônus
                Text(bonusTexto)

                // Contadores de Atributos
                AtributoComContador("Força", forca, { incrementarAtributo("forca") }, { decrementarAtributo("forca") })
                AtributoComContador("Destreza", destreza, { incrementarAtributo("destreza") }, { decrementarAtributo("destreza") })
                AtributoComContador("Constituição", constituicao, { incrementarAtributo("constituicao") }, { decrementarAtributo("constituicao") })
                AtributoComContador("Inteligência", inteligencia, { incrementarAtributo("inteligencia") }, { decrementarAtributo("inteligencia") })
                AtributoComContador("Sabedoria", sabedoria, { incrementarAtributo("sabedoria") }, { decrementarAtributo("sabedoria") })
                AtributoComContador("Carisma", carisma, { incrementarAtributo("carisma") }, { decrementarAtributo("carisma") })

                // Exibir total de pontos restantes
                Text("Pontos restantes: $totalPontos")

                Button(onClick = {
                    // Crie uma instância do ValidadorDePersonagem
                    val validador = ValidadorDePersonagem()

                    // Validação do nome
                    if (validador.validarNome(nome).not()) {
                        Toast.makeText(context, "Nome não pode estar vazio.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Validação dos atributos
                    if (validador.validarAtributos(forca, destreza, constituicao, inteligencia, sabedoria, carisma).not()) {
                        Toast.makeText(context, "Atributos devem ser pelo menos 8.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Criação do personagem
                    val personagem = Personagem().apply {
                        this.nome = nome
                        this.classe = classe
                        this.raca = raca

                        // Atribuir os atributos do personagem com os valores atuais
                        this.forca = forca // Atribuindo o valor da variável de estado a forca do personagem
                        this.destreza = destreza // Atribuindo o valor da variável de estado a destreza do personagem
                        this.constituicao = constituicao // Atribuindo o valor da variável de estado a constituicao do personagem
                        this.inteligencia = inteligencia // Atribuindo o valor da variável de estado a inteligencia do personagem
                        this.sabedoria = sabedoria // Atribuindo o valor da variável de estado a sabedoria do personagem
                        this.carisma = carisma // Atribuindo o valor da variável de estado a carisma do personagem

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

    // Função para incrementar o atributo e reduzir os pontos disponíveis
    private fun incrementarAtributo(atributo: String) {
        when (atributo) {
            "forca" -> if (forca < 15) {
                forca++
                totalPontos--
            }
            "destreza" -> if (destreza < 15) {
                destreza++
                totalPontos--
            }
            "constituicao" -> if (constituicao < 15) {
                constituicao++
                totalPontos--
            }
            "inteligencia" -> if (inteligencia < 15) {
                inteligencia++
                totalPontos--
            }
            "sabedoria" -> if (sabedoria < 15) {
                sabedoria++
                totalPontos--
            }
            "carisma" -> if (carisma < 15) {
                carisma++
                totalPontos--
            }
        }
    }

    // Função para decrementar o atributo e aumentar os pontos disponíveis
    private fun decrementarAtributo(atributo: String) {
        when (atributo) {
            "forca" -> if (forca > 8) {
                forca--
                totalPontos++
            }
            "destreza" -> if (destreza > 8) {
                destreza--
                totalPontos++
            }
            "constituicao" -> if (constituicao > 8) {
                constituicao--
                totalPontos++
            }
            "inteligencia" -> if (inteligencia > 8) {
                inteligencia--
                totalPontos++
            }
            "sabedoria" -> if (sabedoria > 8) {
                sabedoria--
                totalPontos++
            }
            "carisma" -> if (carisma > 8) {
                carisma--
                totalPontos++
            }
        }
    }

    // Componente para exibir atributo com contador
    @Composable
    fun AtributoComContador(nome: String, valor: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("$nome: $valor")
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

    // Componente de seleção de classe
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
                            Text(text = className)
                        }
                    }
                }
            }
        }
    }
}
