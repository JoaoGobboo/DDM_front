package com.example.ddm_front

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Personagem
import com.example.ddm_front.UI.ListaPersonagensActivity
import com.example.ddm_front.UI.PersonagemForm
import com.example.ddm_front.ui.theme.DDMFrontTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import up.ddm.data.AtributosDB

class MainActivity : ComponentActivity() {
    private lateinit var database: AtributosDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = AtributosDB.getDatabase(this)

        setContent {
            MainScreen()
        }
    }

    @Composable
    private fun MainScreen() {
        DDMFrontTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val coroutineScope = rememberCoroutineScope()

                PersonagemForm(
                    onSavePersonagem = { personagem, atributos ->
                        coroutineScope.launch {
                            salvarPersonagem(personagem, atributos)
                        }
                    },
                    onListPersonagens = {
                        startListaPersonagensActivity()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private fun startListaPersonagensActivity() {
        val intent = Intent(this, ListaPersonagensActivity::class.java)
        startActivity(intent)
    }

    private suspend fun salvarPersonagem(personagem: Personagem, atributos: Atributos) {
        try {
            withContext(Dispatchers.IO) {
                val atributosId = database.atributosDAO().insert(atributos)
                personagem.atributosId = atributosId.toInt()
                database.personagemDAO().insert(personagem)
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Personagem criado com sucesso!", Toast.LENGTH_SHORT).show()
                startListaPersonagensActivity()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Erro ao salvar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
