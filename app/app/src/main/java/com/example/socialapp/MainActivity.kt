package com.example.socialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.components.*
import com.example.socialapp.ui.theme.SocialAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialAppTheme {
                AppNavigation()
            }
        }
    }
}

sealed class Screen {
    object Start : Screen()
    object CreateProfile : Screen()
    object ViewProfiles : Screen()
    object MyProfile : Screen()
    object Friends : Screen()
    data class ProfileDetail(val user: User) : Screen()
    data class PostDetail(val post: Post) : Screen()
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Start) }
    var currentUser by remember { mutableStateOf<User?>(null) }

    val users = remember {
        mutableStateListOf(
            User(1, "Катя", "Люблю подорожі та каву", false),
            User(2, "Олексій", "Геймер  та айтішник", true),
            User(3, "Марина", "Обожнюю книги та море", false)
        )
    }

    val posts = remember {
        mutableStateListOf(
            Post(id = 1, authorId = 2, content = "Пройшов Elden Ring без смертей!", comments = listOf("Вау!", "Нереально")),
            Post(id = 2, authorId = 3, content = "Обожнюю книги про психологію", comments = listOf())
        )
    }

    when (val screen = currentScreen) {
        is Screen.Start -> StartScreen(
            onCreateProfile = { currentScreen = Screen.CreateProfile },
            onViewProfiles = { currentScreen = Screen.ViewProfiles }
        )

        is Screen.CreateProfile -> CreateProfileScreen { newUser ->
            val createdUser = newUser.copy(id = 999)
            currentUser = createdUser
            users.add(0, createdUser)
            currentScreen = Screen.MyProfile
        }

        is Screen.MyProfile -> currentUser?.let { user ->
            MyProfileScreen(
                user = user,
                onBack = { currentScreen = Screen.ViewProfiles },
                onAddPost = { post ->
                    val newId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                    posts.add(Post(id = newId, authorId = user.id, content = post))
                },
                onViewFriends = { currentScreen = Screen.Friends },
                posts = posts.filter { it.authorId == user.id },
                onPostClick = { post -> currentScreen = Screen.PostDetail(post) }
            )
        }

        is Screen.Friends -> {
            val friends = users.filter { it.isFriend }
            FriendsScreen(
                friends = friends,
                onBack = { currentScreen = Screen.MyProfile },
                onFriendClick = { user -> currentScreen = Screen.ProfileDetail(user) }
            )
        }

        is Screen.ViewProfiles -> MainScreen(
            users = users,
            currentUser = currentUser,
            onProfileClick = { user -> currentScreen = Screen.ProfileDetail(user) },
            onBack = { currentScreen = Screen.MyProfile }
        )

        is Screen.ProfileDetail -> ProfileDetailScreen(
            user = screen.user,
            onBack = { currentScreen = Screen.ViewProfiles },
            posts = posts,
            onPostClick = { post -> currentScreen = Screen.PostDetail(post) }
        )

        is Screen.PostDetail -> PostDetailScreen(
            post = screen.post,
            onBack = { currentScreen = Screen.MyProfile },
            onAddComment = { comment ->
                val index = posts.indexOfFirst { it.id == screen.post.id }
                if (index != -1) {
                    val updated = posts[index].copy(comments = posts[index].comments + comment)
                    posts[index] = updated
                }
            }
        )
    }
}

@Composable
fun StartScreen(onCreateProfile: () -> Unit, onViewProfiles: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SocialApp!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onCreateProfile,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61))
        ) {
            Text("Створити профіль")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onViewProfiles,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61))
        ) {
            Text("Переглянути профілі")
        }
    }
}
