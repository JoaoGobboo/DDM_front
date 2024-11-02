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
    onPersonagemClick: (Personagem) -> Unit,
    onPersonagemDelete: (Personagem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Personagens") },
                actions = {
                    IconButton(onClick = onAddPersonagem) {
                        Icon(Icons.Filled.Add, "Adicionar Personagem")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (personagens.isEmpty()) {
            EmptyState(
                onAddClick = onAddPersonagem,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(personagens) { (personagem, atributos) ->
                    PersonagemCard(
                        personagem = personagem,
                        atributos = atributos,
                        onClick = { onPersonagemClick(personagem) },
                        onDelete = { onPersonagemDelete(personagem) } // Adicionando a lógica de exclusão
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonagemCard(
    personagem: Personagem,
    atributos: Atributos,
    onClick: () -> Unit,
    onDelete: () -> Unit // Adicionando o parâmetro onDelete
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
                        contentDescription = "Expandir"
                    )
                }
            }

            if (expanded) {
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

@Composable
private fun AtributosGrid(atributos: Atributos) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val atributosMap = listOf(
            "Força" to atributos.getAtributo("Força"),
            "Destreza" to atributos.getAtributo("Destreza"),
            "Constituição" to atributos.getAtributo("Constituição"),
            "Inteligência" to atributos.getAtributo("Inteligência"),
            "Sabedoria" to atributos.getAtributo("Sabedoria"),
            "Carisma" to atributos.getAtributo("Carisma")
        )

        for (i in atributosMap.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AtributoItem(
                    nome = atributosMap[i].first,
                    valor = atributosMap[i].second,
                    modifier = Modifier.weight(1f)
                )
                if (i + 1 < atributosMap.size) {
                    AtributoItem(
                        nome = atributosMap[i + 1].first,
                        valor = atributosMap[i + 1].second,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun AtributoItem(
    nome: String,
    valor: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = nome,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = valor.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
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
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Criar Personagem")
        }
    }
}
