package com.example.socialapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialapp.User

@Composable
fun CreateProfileScreen(onProfileCreated: (User) -> Unit) {
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Створи свій профіль", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Ім'я") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Біо") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val newUser = User(id = 0, name = name, bio = bio, isFriend = false)
            onProfileCreated(newUser)
        }) {
            Text("Зберегти")
        }
    }
}
