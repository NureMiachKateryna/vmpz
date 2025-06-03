package com.example.socialapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialapp.Post
import com.example.socialapp.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    user: User,
    onBack: () -> Unit,
    onAddPost: (String) -> Unit,
    onViewFriends: () -> Unit,
    posts: List<Post>,
    onPostClick: (Post) -> Unit
) {
    var newPost by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мій профіль") },
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
            Text("Імʼя: ${user.name}", style = MaterialTheme.typography.titleMedium)
            Text("Біо: ${user.bio}")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onViewFriends) {
                Text("Мої друзі")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Мої пости:", style = MaterialTheme.typography.titleSmall)

            if (posts.isEmpty()) {
                Text("Ще немає постів.")
            } else {
                posts.forEach { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onPostClick(post) }
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(post.content)
                            if (post.comments.isNotEmpty()) {
                                Spacer(Modifier.height(4.dp))
                                Text("Коментарі: ${post.comments.size}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = newPost,
                onValueChange = { newPost = it },
                label = { Text("Новий пост") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (newPost.isNotBlank()) {
                        onAddPost(newPost)
                        newPost = ""
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Додати пост")
            }
        }
    }
}
