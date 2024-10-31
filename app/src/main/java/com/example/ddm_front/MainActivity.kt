package com.example.ddm_front

import Atributos
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.example.ddm_front.components.PersonagemForm
import com.example.ddm_front.Logica.*
import com.example.ddm_front.Data.AtributosDAO
import com.example.ddm_front.Data.PersonagemDAO
import com.example.ddm_front.Data.AtributosDB
import com.example.ddm_front.Data.RacaDAO
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var atributosDAO: AtributosDAO
    private lateinit var personagemDAO: PersonagemDAO
    private lateinit var racaDAO: RacaDAO // Inicialização do RacaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AtributosDB.getDatabase(this)
        atributosDAO = db.atributosDAO()
        personagemDAO = db.personagemDAO()
        racaDAO = db.racaDAO() // Inicialização do RacaDAO

        setContent {
            MaterialTheme {
                var personagem by remember { mutableStateOf<Personagem?>(null) }

                // Definindo as classes disponíveis com bônus de atributos
                val classes = listOf(
                    Classe("Guerreiro", mapOf("forca" to 3)),
                    Classe("Mago", mapOf("inteligencia" to 3)),
                    Classe("Clérigo", mapOf("sabedoria" to 3)),
                    Classe("Ladino", mapOf("destreza" to 3))
                )

                // Inicializando as raças com bônus de atributos
                val racas = listOf(
                    Raca("Humano", 1, 0, 0, 0, 0, 1),
                    Raca("Elfo", 0, 2, 0, 1, 0, 0)
                    // Adicione outras raças conforme necessário
                )

                // Exibindo o formulário de criação do personagem
                PersonagemForm(
                    onPersonagemCadastrado = { novoPersonagem ->
                        // Calcular pontos de vida depois de aplicar os bônus
                        novoPersonagem.pontosDeVida = calcularPontosDeVida(novoPersonagem.atributos)

                        // Salvar personagem no banco de dados
                        savePersonagem(novoPersonagem)

                        personagem = novoPersonagem
                    },
                    classes = classes,
                    racas = racas
                )

                // Exibir informações do personagem criado
                personagem?.let {
                    Text(text = "Personagem criado: ${it.nome} com ${it.pontosDeVida} pontos de vida")
                }
            }
        }
    }

    private fun calcularPontosDeVida(atributos: Atributos): Int {
        return 10 + ((atributos.constituicao - 10) / 2)
    }

    private fun savePersonagem(personagem: Personagem) {
        lifecycleScope.launch {
            try {
                personagemDAO.insert(personagem)
                // Sucesso na inserção, adicione um log ou feedback ao usuário aqui
            } catch (e: Exception) {
                // Lidar com erro na inserção
                e.printStackTrace() // Exemplo de log de erro
            }
        }
    }
}
