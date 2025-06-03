package com.example.socialapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialapp.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    post: Post,
    onAddComment: (String) -> Unit,
    onBack: () -> Unit
) {
    var comment by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Деталі поста") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {

            Text("Пост:", style = MaterialTheme.typography.titleMedium)
            Text(post.content, modifier = Modifier.padding(bottom = 8.dp))

            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Text("Коментарі:", style = MaterialTheme.typography.titleSmall)
            if (post.comments.isEmpty()) {
                Text("Ще немає коментарів")
            } else {
                post.comments.forEach { comment ->
                    Text("• $comment")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Написати коментар") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (comment.isNotBlank()) {
                        onAddComment(comment)
                        comment = ""
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Додати коментар")
            }
        }
    }
}