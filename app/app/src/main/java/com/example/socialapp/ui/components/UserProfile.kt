package com.example.socialapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.User

@Composable
fun UserProfile(
    user: User,
    onAddFriend: ((User) -> Unit)? = null,
    onNameClick: (User) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color(0xFFFF6F61),
                    fontSize = 20.sp
                ),
                modifier = Modifier.clickable { onNameClick(user) }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.bio, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (onAddFriend != null && !user.isFriend) {
                Button(
                    onClick = { onAddFriend(user) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61))
                ) {
                    Text("Додати в друзі")
                }
            } else if (user.isFriend) {
                Text("✅ У друзях", color = Color.Green)
            }
        }
    }
}
