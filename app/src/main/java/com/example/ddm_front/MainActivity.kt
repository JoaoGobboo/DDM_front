package com.example.ddm_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ddm_front.ui.theme.DDM_FrontTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DDM_FrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Criando a tela de criação de personagem
                    CharacterCreationScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CharacterCreationScreen(modifier: Modifier = Modifier) {
    // Estados para armazenar o nome, classe e atributos do personagem
    var characterName by remember { mutableStateOf("") }
    var characterClass by remember { mutableStateOf("Guerreiro") } // Classe inicial
    var strength by remember { mutableStateOf(10) }
    var dexterity by remember { mutableStateOf(10) }
    var constitution by remember { mutableStateOf(10) }
    var intelligence by remember { mutableStateOf(10) }
    var wisdom by remember { mutableStateOf(10) }
    var charisma by remember { mutableStateOf(10) }

    // Layout da tela
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Criação de Personagem", style = MaterialTheme.typography.titleLarge)

        // Campo de texto para o nome do personagem
        Text(text = "Nome do Personagem:")
        BasicTextField(
            value = characterName,
            onValueChange = { characterName = it },
            modifier = Modifier.fillMaxWidth()
        )

        // Escolha da classe do personagem
        Text(text = "Classe:")
        ClassSelectionDropdown(selectedClass = characterClass, onClassSelected = { characterClass = it })

        // Atributos do personagem
        Text(text = "Atributos:")
        AttributeInput(label = "Força", value = strength, onValueChange = { strength = it })
        AttributeInput(label = "Destreza", value = dexterity, onValueChange = { dexterity = it })
        AttributeInput(label = "Constituição", value = constitution, onValueChange = { constitution = it })
        AttributeInput(label = "Inteligência", value = intelligence, onValueChange = { intelligence = it })
        AttributeInput(label = "Sabedoria", value = wisdom, onValueChange = { wisdom = it })
        AttributeInput(label = "Carisma", value = charisma, onValueChange = { charisma = it })

        // Botão para criar o personagem
        Button(onClick = {
            val characterDetails = """
                Nome: $characterName
                Classe: $characterClass
                Atributos:
                Força: $strength
                Destreza: $dexterity
                Constituição: $constitution
                Inteligência: $intelligence
                Sabedoria: $wisdom
                Carisma: $charisma
            """.trimIndent()
            println("Personagem Criado: \n$characterDetails")
        }) {
            Text("Criar Personagem")
        }
    }
}

@Composable
fun ClassSelectionDropdown(selectedClass: String, onClassSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val classes = listOf("Guerreiro", "Mago", "Clérigo", "Ladino")

    // Box para o dropdown de seleção de classe
    Box {
        // Botão que abre o dropdown
        Button(onClick = { expanded = !expanded }) {
            Text(text = selectedClass)
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            classes.forEach { classOption ->
                DropdownMenuItem(
                    text = { Text(text = classOption) },  // Atualização: passando a função `text`
                    onClick = {
                        onClassSelected(classOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun AttributeInput(label: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label) // Certificando que 'text' está sendo passado corretamente
        BasicTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                onValueChange(newValue.toIntOrNull() ?: 0)
            },
            modifier = Modifier.width(50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterCreationScreenPreview() {
    DDM_FrontTheme {
        CharacterCreationScreen()
    }
}
