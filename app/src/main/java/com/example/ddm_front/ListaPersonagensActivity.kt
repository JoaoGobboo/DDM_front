package com.example.ddm_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ListaPersonagensActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaPersonagensScreen()
        }
    }

    @Composable
    fun ListaPersonagensScreen() {
        // Obtendo a lista de personagens passados pela Intent
        val personagens = intent.getStringArrayExtra("personagens") ?: emptyArray()

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Lista de Personagens", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                if (personagens.isNotEmpty()) {
                    personagens.forEach { personagem ->
                        Text(personagem)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                } else {
                    Text("Nenhum personagem criado.")
                }
            }
        }
    }
}