package com.example.myapplication2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GuessNumberGame()
                }
            }
        }
    }
}

@Composable
fun GuessNumberGame() {
    val secretNumber = remember { Random.nextInt(1, 101) }
    var userInput by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Введи число від 1 до 100") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val guess = userInput.toIntOrNull()
            resultText = when {
                guess == null -> "Введи правильне число!"
                guess < secretNumber -> "Загадане число більше"
                guess > secretNumber -> "Загадане число менше"
                else -> "Ти вгадав!"
            }
        }) {
            Text("Перевірити")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultText, style = MaterialTheme.typography.bodyLarge)
    }
}
