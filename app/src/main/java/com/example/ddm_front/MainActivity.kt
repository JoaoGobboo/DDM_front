package com.example.ddm_front

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Personagem
import com.example.ddm_front.UI.ListaPersonagensActivity
import com.example.ddm_front.UI.PersonagemForm
import com.example.ddm_front.ui.theme.DDMFrontTheme
import up.ddm.data.AtributosDB

class MainActivity : ComponentActivity() {
    private lateinit var database: AtributosDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = AtributosDB.getDatabase(this) // Inicializa o banco de dados

        setContent {
            MainScreen() // Chama a função MainScreen
        }
    }

    @Composable
    private fun MainScreen() {
        DDMFrontTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val coroutineScope = rememberCoroutineScope() // Cria um escopo de coroutine

                Column(modifier = Modifier.padding(16.dp)) {
                    PersonagemForm(
                        onSavePersonagem = { personagem, atributos ->
                            coroutineScope.launch {
                                salvarPersonagem(personagem, atributos) // Chama a função para salvar o personagem
                            }
                        },
                        onListPersonagens = {
                            startListaPersonagensActivity() // Chama a função para listar personagens
                        }
                    )
                }
            }
        }
    }

    private fun startListaPersonagensActivity() {
        val intent = Intent(this, ListaPersonagensActivity::class.java)
        startActivity(intent) // Inicia a atividade de lista de personagens
    }

    private suspend fun salvarPersonagem(personagem: Personagem, atributos: Atributos) {
        try {
            withContext(Dispatchers.IO) {
                val atributosId = database.atributosDAO().insert(atributos) // Insere atributos
                personagem.atributosId = atributosId.toInt() // Armazena o ID dos atributos
                database.personagemDAO().insert(personagem) // Insere personagem
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Personagem criado com sucesso!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Erro ao salvar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
