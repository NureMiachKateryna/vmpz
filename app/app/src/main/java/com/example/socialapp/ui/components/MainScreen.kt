package com.example.socialapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialapp.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    users: SnapshotStateList<User>,
    currentUser: User?,
    onProfileClick: (User) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Користувачі") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(users.filter { it.id != currentUser?.id }) { user ->
                UserProfile(
                    user = user,
                    onAddFriend = if (currentUser != null) { addedUser ->
                        val index = users.indexOf(addedUser)
                        users[index] = addedUser.copy(isFriend = true)
                    } else null,
                    onNameClick = onProfileClick
                )
            }
        }
    }
}