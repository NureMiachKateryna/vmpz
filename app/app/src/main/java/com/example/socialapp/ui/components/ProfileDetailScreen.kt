package com.example.socialapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.socialapp.User
import com.example.socialapp.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailScreen(
    user: User,
    onBack: () -> Unit,
    posts: List<Post> = listOf(),
    onPostClick: (Post) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(user.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Біо:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(user.bio)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Пости:", fontWeight = FontWeight.Bold)

            val userPosts = posts.filter { it.authorId == user.id }
            if (userPosts.isEmpty()) {
                Text("Користувач ще не додав постів")
            } else {
                userPosts.forEach { post ->
                    Text(
                        text = "• ${post.content}",
                        modifier = Modifier.clickable { onPostClick(post) }
                    )
                }
            }
        }
    }
}
