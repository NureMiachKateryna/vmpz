package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DifferenceCalculator()
                }
            }
        }
    }
}

@Composable
fun DifferenceCalculator() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Перше число") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Друге число") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val a = num1.toIntOrNull()
            val b = num2.toIntOrNull()
            result = if (a != null && b != null) {
                "Різниця: ${a - b}"
            } else {
                "Будь ласка, введіть коректні числа"
            }
        }) {
            Text("Обчислити різницю")
        }

        Spacer(modifier = Modifier.height(16.dp))

        result?.let {
            Text(text = it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
