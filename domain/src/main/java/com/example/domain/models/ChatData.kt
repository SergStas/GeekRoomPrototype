package com.example.domain.models

data class ChatData(
    val title: String,
    val avatarUrl: String,
    val participants: List<UserData>,
    val messages: List<MessageData>,
)
