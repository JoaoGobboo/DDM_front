package com.example.ddm_front.UI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Personagem
import up.ddm.data.AtributosDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaPersonagensActivity : ComponentActivity() {
    private lateinit var database: AtributosDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = AtributosDB.getDatabase(this)

        setContent {
            var personagens by remember { mutableStateOf(listOf<Pair<Personagem, Atributos>>()) }
            var isLoading by remember { mutableStateOf(true) } // Estado de carregamento
            var errorMessage by remember { mutableStateOf<String?>(null) } // Mensagem de erro

            // Busca os personagens quando a Activity é criada
            LaunchedEffect(Unit) {
                try {
                    personagens = obterPersonagens()
                } catch (e: Exception) {
                    errorMessage = "Erro ao carregar personagens: ${e.message}"
                } finally {
                    isLoading = false // Atualiza o estado de carregamento
                }
            }

            // Exibe loading ou erro antes de mostrar a lista
            if (isLoading) {
                Text("Carregando personagens...")
            } else if (errorMessage != null) {
                Text(errorMessage!!) // Exibe a mensagem de erro
            } else {
                PersonagemListScreen(
                    personagens = personagens,
                    onAddPersonagem = {
                        // Navegar para a tela de adicionar personagem
                    },
                    onPersonagemClick = { personagem ->
                        // Navegar para a tela de edição do personagem
                    },
                    onPersonagemDelete = { personagem ->
                        // Função para deletar o personagem
                        deletePersonagem(personagem)
                    }
                )
            }
        }
    }

    private suspend fun obterPersonagens(): List<Pair<Personagem, Atributos>> {
        val personagensList = mutableListOf<Pair<Personagem, Atributos>>()

        val personagens = withContext(Dispatchers.IO) {
            database.personagemDAO().getAll() // Certifique-se que este método está definido no DAO
        }

        // Para cada personagem, busque os atributos correspondentes e adicione à lista
        for (personagem in personagens) {
            val atributos = withContext(Dispatchers.IO) {
                database.atributosDAO().getById(personagem.atributosId.toLong())
            }
            personagensList.add(personagem to atributos) // Adiciona à lista
        }

        return personagensList
    }

    private fun deletePersonagem(personagem: Personagem) {
        lifecycleScope.launch(Dispatchers.IO) {
            // Lógica para deletar o personagem do banco de dados
            database.personagemDAO().delete(personagem) // Certifique-se de que este método está definido no DAO
            // Após a exclusão, recarregar a lista de personagens
            val updatedPersonagens = obterPersonagens()
            // Atualizar a lista de personagens no Composable (pode ser necessário usar um estado)
        }
    }
}
