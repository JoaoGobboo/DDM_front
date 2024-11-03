package com.example.ddm_front.UI

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Personagem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonagemListScreen(
    personagens: List<Pair<Personagem, Atributos>>,
    onAddPersonagem: () -> Unit,
    onPersonagemClick: (Pair<Personagem, Atributos>) -> Unit,
    onPersonagemDelete: (Pair<Personagem, Atributos>) -> Unit
) {
    Column {
        Button(onClick = onAddPersonagem) {
            Text("Adicionar Personagem")
        }

        LazyColumn {
            items(personagens) { (personagem, atributos) -> // Desestruturando o Pair
                PersonagemCard(
                    personagem = personagem,
                    atributos = atributos,
                    onClick = { onPersonagemClick(personagem to atributos) }, // Passando o Pair
                    onDelete = { onPersonagemDelete(personagem to atributos) } // Passando o Pair
                )
            }
        }
    }
}


@Composable
private fun EmptyState(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nenhum personagem criado",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onAddClick) {
            Icon(
                Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Criar Personagem")
        }
    }
}

@Composable
fun AtributosGrid(atributos: Atributos) {
    Column {
        // Exibe todos os atributos e seus valores
        atributos.atributosValores.forEach { (nome, valor) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = nome, style = MaterialTheme.typography.bodyMedium)
                Text(text = valor.toString(), style = MaterialTheme.typography.bodyMedium)
            }
        }
        // Exibe os pontos disponíveis
        Text("Pontos Disponíveis: ${atributos.getPontosDisponiveis()}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PersonagemCard(
    personagem: Personagem,
    atributos: Atributos,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = personagem.nome,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Colapsar" else "Expandir"
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))

                // Exibindo a Raça, Classe e Pontos de Vida
                Text("Raça: ${personagem.racaId}", style = MaterialTheme.typography.bodyMedium)
                Text("Classe: ${personagem.classeId}", style = MaterialTheme.typography.bodyMedium)
                Text("Pontos de Vida: ${personagem.pontosDeVida}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))
                AtributosGrid(atributos)

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onClick,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Editar")
                    }
                    TextButton(onClick = onDelete) {
                        Text("Excluir")
                    }
                }
            }
        }
    }
}

