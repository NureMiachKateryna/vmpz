package com.example.socialapp

data class Post(
    val id: Int,
    val authorId: Int,
    val content: String,
    val comments: List<String> = listOf()
)