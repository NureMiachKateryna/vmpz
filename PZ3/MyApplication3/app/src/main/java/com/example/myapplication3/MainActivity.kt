package com.example.myapplication3

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val context = androidx.compose.ui.platform.LocalContext.current

    fun saveToFile(operation: String, result: String) {
        val file = File(context.filesDir, "calc_history.txt")
        val entry = "$operation = $result\n"
        FileOutputStream(file, true).bufferedWriter().use { it.write(entry) }
    }

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

        val operations = listOf("+", "-", "*", "/", "^", "%")

        operations.forEach { op ->
            Button(
                onClick = {
                    val a = num1.toDoubleOrNull()
                    val b = num2.toDoubleOrNull()
                    if (a != null && b != null) {
                        val res = when (op) {
                            "+" -> a + b
                            "-" -> a - b
                            "*" -> a * b
                            "/" -> if (b != 0.0) a / b else "Ділення на нуль"
                            "^" -> Math.pow(a, b)
                            "%" -> a % b
                            else -> "?"
                        }
                        result = res.toString()
                        saveToFile("$a $op $b", result)
                    } else {
                        result = "Невірні вхідні дані"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                Text("Обчислити $op")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Результат: $result", style = MaterialTheme.typography.bodyLarge)
    }
}
